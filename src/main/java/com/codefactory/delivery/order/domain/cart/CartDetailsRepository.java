package com.codefactory.delivery.order.domain.cart;

import com.codefactory.delivery.user.domain.UserId;

import java.util.List;

public interface CartDetailsRepository {
    List<Cart> findAll(UserId userId, CartType type);
    List<Cart> findByIds(List<CartId> cartIds);
}
