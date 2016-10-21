package com.tenx.ms.retail.product.domain;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class ProductEntity {
    @Id
    @GeneratedValue
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private StoreEntity store;

    @OneToOne(targetEntity = StockEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = true)
    private StockEntity stock;

    @OneToMany(targetEntity = OrderProductEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_product_id")
    private List<OrderProductEntity> orderProducts;
}

