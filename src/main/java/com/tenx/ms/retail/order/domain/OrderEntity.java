package com.tenx.ms.retail.order.domain;

import com.tenx.ms.retail.order.rest.dto.Status;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "purchase_order")
public class OrderEntity {
    @Id
    @GeneratedValue
    @Column(name="order_id", nullable = false)
    private Long orderId;

    @Column(name="order_status", nullable = false, length = 7)
    private Status status;

    @Column(name = "order_date", columnDefinition = "datetime default current_timestamp",
        insertable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @OneToMany(targetEntity = OrderProductEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderProductEntity> products;
}
