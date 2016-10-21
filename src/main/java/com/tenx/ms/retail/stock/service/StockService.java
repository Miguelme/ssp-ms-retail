package com.tenx.ms.retail.stock.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    private final static EntityConverter<Stock, StockEntity> STOCKCONVERTER = new EntityConverter<>(Stock.class, StockEntity.class);

    private Long saveStock(Stock stock, ProductEntity productEntity, StoreEntity storeEntity) {
        StockEntity stockEntity = STOCKCONVERTER.toT2(stock);
        stockEntity.setProductId(productEntity.getProductId());
        stockEntity.setProduct(productEntity);
        stockEntity.setStore(storeEntity);
        stockRepository.save(stockEntity);
        return stockEntity.getProductId();
    }

    @Transactional
    public Long addUpdateProductQuantity(Long storeId, Long productId, Stock stock) throws NoSuchElementException {


        Optional<StoreEntity> storeEntity = storeRepository.findOneByStoreId(storeId);
        Optional<ProductEntity> productEntity = productRepository.findOneByProductId(productId);

        if (!storeEntity.isPresent()) {
            throw new NoSuchElementException("The store was not found");
        }
        if (!productEntity.isPresent()) {
            throw new NoSuchElementException("The product was not found");
        }

        return saveStock(stock, productEntity.get(), storeEntity.get());
    }

}
