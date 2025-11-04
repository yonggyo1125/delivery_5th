package com.codefactory.delivery.store.presentation.dto;

import com.codefactory.delivery.menu.domain.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ItemRequest(
        @Size Integer price,
        @NotBlank String name,
        ItemStatus status,
        boolean active,
        Integer stock,
        List<ItemOptionRequest> itemOptions
) {
}
