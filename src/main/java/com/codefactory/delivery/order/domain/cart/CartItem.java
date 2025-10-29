package com.codefactory.delivery.order.domain.cart;

import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Embedded
    @Column(name="item_id")
    private ItemId id;

    @Column(length=60)
    private String itemName;

    @Convert(converter = PriceConverter.class)
    private Price itemPrice;

    @Builder
    public CartItem(ItemId id, String itemName,Price itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
