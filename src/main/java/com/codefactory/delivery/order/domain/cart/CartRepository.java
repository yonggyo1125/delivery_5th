package com.codefactory.delivery.order.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, CartId> {
}