package com.tenx.ms.retail.store.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
