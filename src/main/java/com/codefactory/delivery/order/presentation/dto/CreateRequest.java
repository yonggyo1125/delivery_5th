package com.codefactory.delivery.order.presentation.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateRequest(
        String name,
        String email,
        String address,
        String memo,
        List<CreateItemDto> items
) {}
