package com.codefactory.delivery.order.domain.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartId {
    @Column(length=45, name="cart_id")
    private UUID id;

    public CartId(UUID id) {
        this.id = id;
    }

    public static CartId of() {
        return CartId.of(UUID.randomUUID());
    }

    public static CartId of(UUID id) {
        return new CartId(id);
    }
}
