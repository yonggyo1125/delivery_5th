package com.codefactory.delivery.order.domain.cart;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Cart {
    @EmbeddedId
    private CartId id;

//    private String mode; // direct, cart
//
//    @Embedded
//    private UserId userId;
//
//    @Embedded
//    private ProductId productId;
//
//    private int quantity;
}
