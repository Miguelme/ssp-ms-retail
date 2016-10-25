package com.tenx.ms.retail.order.repository;

import com.tenx.ms.retail.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
