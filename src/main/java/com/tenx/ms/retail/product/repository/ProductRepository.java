package com.tenx.ms.retail.product.repository;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findOneByProductId(final Long storeId);
    Optional<ProductEntity> findByStoreStoreIdAndProductId(final Long storeId, final Long productId);
    List<ProductEntity> findByStoreStoreId(final Long storeId);
    List<ProductEntity> findAll();
}
