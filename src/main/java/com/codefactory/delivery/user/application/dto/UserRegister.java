package com.codefactory.delivery.user.application.dto;

import lombok.Builder;

@Builder
public record UserRegister(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String mobile
) {}