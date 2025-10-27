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
    @Column(length=100)
    private String address;

    @Lob
    private String memo;

    private double latitude;

    private double longitude;

    @Builder
    public DeliveryInfo(String address, String memo) {
        this.address = address;
        this.memo = memo;
    }

}
