package com.codefactory.delivery.order.domain.cart;

import com.codefactory.delivery.menu.domain.ItemId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
    private int itemPrice;

    @Builder
    public CartItem(ItemId id, String itemName,int itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
