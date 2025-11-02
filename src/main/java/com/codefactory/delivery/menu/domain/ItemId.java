package com.codefactory.delivery.menu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemId {
    @Column(name="item_id")
    private UUID id;

    public ItemId(UUID id) {
        this.id = id;
    }

    public static ItemId of() {
        return ItemId.of(UUID.randomUUID());
    }

    public static ItemId of(UUID id) {
        return new ItemId(id);
    }
}
