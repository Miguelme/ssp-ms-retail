package com.tenx.ms.retail.order.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderProduct;
import com.tenx.ms.retail.order.rest.dto.Status;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    private final static EntityConverter<Order, OrderEntity> ORDERCONVERTER = new EntityConverter<>(Order.class, OrderEntity.class);
    private final static EntityConverter<OrderProduct, OrderProductEntity> ORDERPRODUCTCONVERTER = new EntityConverter<>(OrderProduct.class, OrderProductEntity.class);

    @Transactional
    public Order createOrder(Long storeId, Order order) throws NoSuchElementException {

        Optional<StoreEntity> storeEntity = storeRepository.findOneByStoreId(storeId);
        storeEntity.orElseThrow( () -> new NoSuchElementException("The store was not found"));

        order.setStoreId(storeId);
        order.setStatus(Status.ORDERED);

        List<OrderProductEntity> soldProductsEntities = new ArrayList<>();
        List<OrderProduct> backorderedProducts = new ArrayList<>();
        List<OrderProduct> soldProducts = new ArrayList<>();

        for (OrderProduct product: order.getProducts()) {
            Optional<StockEntity> stockOptional = stockRepository.findOneByProductId(product.getProductId());
            if (stockOptional.isPresent()) {
                StockEntity stock =  stockOptional.get();
                if (discountFromStock(product.getCount(), stock)) {
                    addToSoldProducts(product, soldProducts, soldProductsEntities, stock.getProduct());
                } else {
                    backorderedProducts.add(product);
                }
            } else {
                backorderedProducts.add(product);
            }
        }

        return saveOrder(order, backorderedProducts, soldProducts, soldProductsEntities, storeEntity.get());
    }

    private boolean discountFromStock(Long orderCount, StockEntity stock) {
        if (orderCount <= stock.getCount()) {
            stock.setCount(stock.getCount() - orderCount);
            stockRepository.save(stock);
            return true;
        } else {
            return false;
        }
    }

    private Order saveOrder(Order order, List<OrderProduct> backorderedProducts, List<OrderProduct> soldProducts,
                            List<OrderProductEntity> soldProductsEntities, StoreEntity store){
        order.setBackorderedProducts(backorderedProducts);
        order.setSoldProducts(soldProducts);
        OrderEntity orderEntity = ORDERCONVERTER.toT2(order);
        orderEntity.setStore(store);
        orderEntity.setProducts(soldProductsEntities);
        orderRepository.save(orderEntity);
        return order;
    }
    private void addToSoldProducts(OrderProduct product, List<OrderProduct> soldProducts, List<OrderProductEntity> soldProductsEntities, ProductEntity productSold) {
        OrderProductEntity orderProductEntity = ORDERPRODUCTCONVERTER.toT2(product);
        orderProductEntity.setProduct(productSold);
        soldProducts.add(product);
        soldProductsEntities.add(orderProductEntity);

    }
}
