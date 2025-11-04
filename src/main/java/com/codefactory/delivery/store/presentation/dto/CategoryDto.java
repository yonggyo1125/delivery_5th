package com.codefactory.delivery.store.presentation.dto;

import com.codefactory.delivery.store.domain.Category;

public record CategoryDto(
        Category category,
        boolean active
) {}
