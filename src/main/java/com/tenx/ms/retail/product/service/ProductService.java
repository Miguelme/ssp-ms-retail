package com.tenx.ms.retail.product.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
        storeEntity.orElseThrow( () -> new NoSuchElementException("The store was not found"));
        return saveProduct(productEntity, storeEntity.get());

    }


    public List<Product> findProducts(Pageable pageable, Long storeId){
        Optional<StoreEntity> storeEntity = storeRepository.findOneByStoreId(storeId);
        storeEntity.orElseThrow( () -> new NoSuchElementException("The store was not found"));
        List<ProductEntity> productEntities = productRepository.findByStoreStoreId(pageable, storeId).getContent();
        return productEntities.stream().map(PRODUCTCONVERTER::toT1).collect(Collectors.toList());

    }

    public Optional<Product> findProductByStoreIdAndProductId(Long storeId, Long productId) {
        Optional<Product> productOptional = productRepository.findByStoreStoreIdAndProductId(storeId, productId).map(PRODUCTCONVERTER::toT1);
        productOptional.orElseThrow( () -> new NoSuchElementException("The store or the product was not found"));
        return productOptional;

    }

}
