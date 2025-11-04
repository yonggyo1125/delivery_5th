package com.codefactory.delivery.store.infrastructure.persistence.converter;

import com.codefactory.delivery.store.domain.Staff;
import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StaffConverter implements AttributeConverter<List<Staff>, String> {
    @Override
    public String convertToDatabaseColumn(List<Staff> attribute) {
        return attribute == null ? null : attribute.stream().map(s -> s.getId().getId().toString()).collect(Collectors.joining(","));
    }

    @Override
    public List<Staff> convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? Arrays.stream(dbData.split(","))
                .map(s -> new Staff(UserId.of(UUID.fromString(s)))).toList() : null;
    }
}
