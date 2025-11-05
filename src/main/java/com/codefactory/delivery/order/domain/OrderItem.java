package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

/**
 * 선택한 옵션
 *  옵션번호_금액_수량||옵션번호_금액_수량...
 */
@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Embedded
    private ItemId itemId;

    @Column(nullable = false, length=60)
    private String itemName; // 주문상품명

    @Convert(converter = PriceConverter.class)
    private Price price;

    private int quantity;

    @Convert(converter = PriceConverter.class)
    private Price totalPrice;

    @Builder
    public OrderItem(ItemId itemId, Price price, int quantity) {
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    private Price calculateTotalPrice() {
        return price.multiply(quantity);
    }
}
