package com.codefactory.delivery.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
public class OrderId {
    @Column(name="order_id")
    private UUID id;

    protected OrderId() {}

    public OrderId(UUID id) {
        this.id = id;
    }

    public static OrderId of(UUID id) {
        return new OrderId(id);
    }

}
