package com.codefactory.delivery.order.infrastructure.persistence.converter;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.order.domain.OrderItemOption;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
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
        return StringUtils.hasText(dbData) ? Arrays.stream(dbData.split("\\|\\|"))
                .map(s -> {
                    String[] values = s.split("_");
                    return OrderItemOption.builder()
                            .name(values[0])
                            .price(new Price(Integer.parseInt(values[1])))
                            .quantity(Integer.parseInt(values[2]))
                            .build();
                }).toList() : null;
    }
}
