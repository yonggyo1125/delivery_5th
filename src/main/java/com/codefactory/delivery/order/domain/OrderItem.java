package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.order.infrastructure.persistence.converter.PriceConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Embedded
    private ItemId itemId;

    @Column(length=60)
    private String itemName; // 메뉴명

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
