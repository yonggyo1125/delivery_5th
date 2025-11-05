package com.codefactory.delivery.order.presentation.dto;

import java.util.List;
import java.util.UUID;

public record CreateItemDto(
        UUID itemId, // 메뉴 번호
        int quantity, // 구매 수량
        List<CreateItemOptionDto> options
) {}
