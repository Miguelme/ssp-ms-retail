package com.tenx.ms.retail.order.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_product")
@Data
public class OrderProductEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id", nullable = false, insertable = false, updatable = false)
    private Long orderProductId;

    @Column(name = "count", nullable = false)
    private Long count;

    @OneToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id",  referencedColumnName = "product_id", updatable = false)
    private ProductEntity product;

    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private OrderEntity order;
}
