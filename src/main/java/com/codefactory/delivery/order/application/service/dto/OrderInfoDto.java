package com.codefactory.delivery.order.application.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderInfoDto(
        UUID ordererId,
        String ordererName,
        String ordererEmail,
        String deliveryAddress,
        String deliveryMemo
) {}
