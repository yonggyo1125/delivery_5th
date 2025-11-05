package com.codefactory.delivery.order.application.service.dto;

import java.util.List;
import java.util.UUID;

public record OrderItemDto(
    UUID itemId, // 메뉴 번호
    List<OrderItemOptionDto> options
){}
