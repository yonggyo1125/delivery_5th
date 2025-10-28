package com.codefactory.delivery.order.infrastructure.persistence;

import com.codefactory.delivery.order.domain.cart.*;
import com.codefactory.delivery.user.domain.UserId;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

@Repository
@RequiredArgsConstructor
public class CartDetailsDao implements CartDetailsRepository {

    private final CartRepository repository;

    @Override
    public List<Cart> findAll(UserId userId, CartType type) {
        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cart.userId.eq(userId));
        if (type != null) builder.and(cart.type.eq(type));

        return (List<Cart>)repository.findAll(builder, Sort.by(asc("createdAt")));
    }

    @Override
    public List<Cart> findByIds(List<CartId> cartIds) {
        return repository.findAllById(cartIds);
    }
}
