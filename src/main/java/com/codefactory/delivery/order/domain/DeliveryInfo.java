package com.codefactory.delivery.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.*;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryInfo {
    @Column(name="delivery_address", length=100)
    private String address;

    @Lob
    @Column(name="delivery_memo")
    private String memo;

    @Builder
    protected DeliveryInfo(String address, String memo) {
        this.address = address;
        this.memo = memo;
    }
}
