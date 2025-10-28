package com.codefactory.delivery.order.domain.cart;

import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.UUID)
    private CartId id;

    @Enumerated(EnumType.STRING)
    @Column(length=25)
    private CartType type;

    @Embedded
    private UserId userId;

    @Embedded
    private CartItem item;

    private int quantity;

    private int totalPrice;

    @Builder
    public Cart(CartType type, UserId userId, CartItem item, int quantity) {
        this.type = Objects.requireNonNullElse(type, CartType.DIRECT); // type의 기본값은 바로구매
        this.userId = userId;
        this.quantity = quantity;
        setCartItem(item);
        calculateTotalPrice();
    }

    // 장바구니 상품이 없다면 담을 수 없음
    private void setCartItem(CartItem item) {
        this.item = item;
    }

    // 장바구니 상품별 총 합계
    private void calculateTotalPrice() {
        if (item == null) return;

        totalPrice = item.getItemPrice() * quantity;
    }
}
