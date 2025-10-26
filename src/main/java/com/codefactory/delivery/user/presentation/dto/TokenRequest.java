package com.codefactory.delivery.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {}
