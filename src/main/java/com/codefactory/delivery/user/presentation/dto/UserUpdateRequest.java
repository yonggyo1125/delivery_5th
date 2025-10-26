package com.codefactory.delivery.user.presentation.dto;

import jakarta.validation.constraints.Email;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        @Email
        String email,
        String mobile
) {}
