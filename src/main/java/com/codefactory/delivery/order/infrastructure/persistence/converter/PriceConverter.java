package com.codefactory.delivery.order.infrastructure.persistence.converter;

import com.codefactory.delivery.order.domain.Price;
import jakarta.persistence.AttributeConverter;

import java.util.Objects;

public class PriceConverter implements AttributeConverter<Price, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Price price) {
        return price == null ? 0 : price.getValue();
    }

    @Override
    public Price convertToEntityAttribute(Integer value) {
        return new Price(Objects.requireNonNullElse(value, 0));
    }
}
