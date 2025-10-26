package com.codefactory.delivery.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank
        @Size(min=8)
        String password,

        @NotBlank
        String confirmPassword
) {}