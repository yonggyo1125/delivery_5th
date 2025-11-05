package com.codefactory.delivery.store.presentation.dto;

import com.codefactory.delivery.menu.domain.ItemStatus;
import com.codefactory.delivery.store.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ItemRequest(
        @Size Integer price,
        @NotBlank String name,
        Category category,
        ItemStatus status,
        boolean active,
        Integer stock,
        List<ItemOptionRequest> itemOptions
) {
}
