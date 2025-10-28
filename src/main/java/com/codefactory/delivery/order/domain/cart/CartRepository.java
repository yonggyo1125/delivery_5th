package com.codefactory.delivery.order.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CartRepository extends JpaRepository<Cart, CartId>, QuerydslPredicateExecutor<Cart> {
}