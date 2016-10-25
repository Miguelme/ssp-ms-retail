package com.tenx.ms.retail.stock.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock")
@Data
public class StockEntity {

    @Id
    @Column(name="product_id", nullable = false)
    private Long productId;

    @OneToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    private StoreEntity store;

    @Column(name = "count", nullable = false)
    private Long count;

}
