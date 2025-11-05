package com.codefactory.delivery.order.infrastructure.persistence.converter;

import com.codefactory.delivery.order.domain.OrderItemOption;
import jakarta.persistence.AttributeConverter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 옵션이름_금액_수량||옵션이름_금액_수량..
 *
 */
public class OrderItemOptionConverter implements AttributeConverter<List<OrderItemOption>, String> {
    @Override
    public String convertToDatabaseColumn(List<OrderItemOption> attribute) {
        return attribute == null ? null : attribute.stream()
                .map(o -> String.format("%s_%d_%d", o.getName(), o.getPrice().getValue(), o.getQuantity())).collect(Collectors.joining("||"));

    }

    @Override
    public List<OrderItemOption> convertToEntityAttribute(String dbData) {
        return List.of();
    }
}
