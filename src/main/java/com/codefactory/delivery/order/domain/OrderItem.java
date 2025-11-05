package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.order.infrastructure.persistence.converter.OrderItemOptionConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.util.List;

/**
 * 선택한 옵션
 *  옵션명_금액_수량||옵션명_금액_수량...
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

    @Column(length=500)
    @Convert(converter = OrderItemOptionConverter.class)
    private List<OrderItemOption> options;

    @Convert(converter = PriceConverter.class)
    private Price totalPrice;

    @Builder
    public OrderItem(ItemId itemId, Price price, int quantity, List<OrderItemOption> options) {
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.options = options;
        this.totalPrice = calculateTotalPrice();
    }

    private Price calculateTotalPrice() {
        int optionPrice = options == null ? 0 : options.stream().mapToInt(o -> o.getTotalPrice().getValue()).sum();

        return price.multiply(quantity).add(new Price(optionPrice));
    }
}
