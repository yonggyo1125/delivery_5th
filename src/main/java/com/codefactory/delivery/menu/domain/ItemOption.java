package com.codefactory.delivery.menu.domain;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemOption {

    @Column(nullable = false, length=60)
    private String optionName;

    @Convert(converter = PriceConverter.class)
    private Price addPrice;

    @Builder
    public ItemOption(String optionName, Price addPrice) {
        this.optionName = optionName;
        this.addPrice = addPrice;
    }
}
