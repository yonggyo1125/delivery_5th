package com.codefactory.delivery.order.application.service.dto;

import java.util.UUID;

public record OrderCreateDto(
        UUID orderId,
        String ordererName,
        String ordererEmail

) {}
