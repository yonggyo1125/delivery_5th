package com.codefactory.delivery.menu.presentation.dto;

import com.codefactory.delivery.menu.domain.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ItemRequest(
        @Size Integer price,
        @NotBlank String name,
        ItemStatus status,
        boolean active,
        Integer stock
) {
}
