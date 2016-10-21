package com.tenx.ms.retail.product.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private final static EntityConverter<Product, ProductEntity> PRODUCTCONVERTER = new EntityConverter<>(Product.class, ProductEntity.class);

    private Long saveProduct(ProductEntity productEntity, StoreEntity storeEntity) {
        productEntity.setStore(storeEntity);
        productRepository.save(productEntity);
        return productEntity.getProductId();
    }

    @Transactional
    public Long createProduct(Product product) throws NoSuchElementException {

        ProductEntity productEntity = PRODUCTCONVERTER.toT2(product);
        Optional<StoreEntity> storeEntity = storeRepository.findOneByStoreId(product.getStoreId());

        if (storeEntity.isPresent()){
            return saveProduct(productEntity, storeEntity.get());
        } else {
            throw new NoSuchElementException("The store was not found");
        }
    }


    public List<Product> findProducts(Long storeId){
        Optional<StoreEntity> storeEntity = storeRepository.findOneByStoreId(storeId);

        if (storeEntity.isPresent()){
            List<ProductEntity> productEntities = productRepository.findByStoreStoreId(storeId);
            return productEntities.stream().map(PRODUCTCONVERTER::toT1).collect(Collectors.toList());
        }else{
            throw new NoSuchElementException("The store was not found");
        }
    }

    public Optional<Product> findProductByStoreIdAndProductId(Long storeId, Long productId) {
        Optional<Product> productOptional = productRepository.findByStoreStoreIdAndProductId(storeId, productId).map(PRODUCTCONVERTER::toT1);
        if (productOptional.isPresent()) {
            return productOptional;
        } else {
            throw new NoSuchElementException("The store or the product was not found");
        }
    }

}
