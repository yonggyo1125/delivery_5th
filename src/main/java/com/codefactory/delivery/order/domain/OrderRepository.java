package com.codefactory.delivery.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderRepository extends JpaRepository<Order, OrderId>, QuerydslPredicateExecutor<Order> {
}
