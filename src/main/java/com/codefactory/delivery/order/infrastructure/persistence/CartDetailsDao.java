package com.codefactory.delivery.order.infrastructure.persistence;

import com.codefactory.delivery.order.domain.cart.Cart;
import com.codefactory.delivery.order.domain.cart.CartDetailsRepository;
import com.codefactory.delivery.order.domain.cart.QCart;
import com.codefactory.delivery.user.domain.UserId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartDetailsDao implements CartDetailsRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Cart> findAll(UserId userId) {
        QCart cart = QCart.cart;

        return queryFactory.selectFrom(cart)
                .where(cart.userId.eq(userId))
                .orderBy(cart.createdAt.asc())
                .fetch();
    }
}
