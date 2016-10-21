package com.tenx.ms.retail.store.domain;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "store")
@Data
public class StoreEntity {

    @Id
    @GeneratedValue
    @Column(name="store_id", nullable = false)
    private Long storeId;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(targetEntity = ProductEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_id", nullable = false, insertable = false, updatable = false)
    private List<ProductEntity> products;

    @OneToMany(targetEntity = StockEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_id", nullable = false, insertable = false, updatable = false)
    private List<StockEntity> stocks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_id", nullable = false, insertable = false, updatable = false)
    private List<OrderEntity> orders;

}
