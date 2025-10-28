package com.codefactory.delivery.order.domain.cart;

import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.order.domain.Price;
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

    @AttributeOverrides(
            @AttributeOverride(name="value", column = @Column(name="item_price"))
    )
    private Price itemPrice;

    @Builder
    public CartItem(ItemId id, String itemName,Price itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
