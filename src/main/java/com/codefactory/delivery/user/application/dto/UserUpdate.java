package com.codefactory.delivery.user.application.dto;

import lombok.Builder;

@Builder
public record UserUpdate(
        String firstName,
        String lastName,
        String email,
        String mobile
) {}