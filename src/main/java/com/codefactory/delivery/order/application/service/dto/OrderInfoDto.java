package com.codefactory.delivery.order.application.service.dto;

import java.util.UUID;

public record OrderInfoDto(
        UUID ordererId,
        String ordererName,
        String ordererEmail,
        String deliveryAddress,
        String deliveryMemo
) {}
