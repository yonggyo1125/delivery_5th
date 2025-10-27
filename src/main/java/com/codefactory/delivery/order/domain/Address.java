package com.codefactory.delivery.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Column(length=100)
    private String address;
    @Column(length=100)
    private String addressSub;

    @Builder
    public Address(String address, String addressSub) {
        this.address = address;
        this.addressSub = addressSub;
    }
}
