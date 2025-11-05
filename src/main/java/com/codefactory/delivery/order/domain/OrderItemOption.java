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
    private String name;

    @Convert(converter = PriceConverter.class)
    private Price price;
    private int quantity;

    private Price totalPrice;

    @Builder
    protected OrderItemOption(String name, Price price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.totalPrice = new Price(price.multiply(quantity).getValue());
    }
}
