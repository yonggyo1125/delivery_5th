package com.codefactory.delivery.order.application.service.dto;

import java.util.UUID;

public record OrderInfoDto(
        UUID orderId,
        String ordererName,
        String ordererEmail
) {}
