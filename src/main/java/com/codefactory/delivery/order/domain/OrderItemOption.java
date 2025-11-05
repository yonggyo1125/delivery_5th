package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemOption {
    private int idx;
    @Convert(converter = PriceConverter.class)
    private Price price;
    private int quantity;

    private Price totalPrice;

    @Builder
    protected OrderItemOption(int idx, Price price, int quantity) {
        this.idx = idx;
        this.price = price;
        this.quantity = quantity;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.totalPrice = new Price(price.multiply(quantity).getValue());
    }
}
