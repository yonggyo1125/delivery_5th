package com.codefactory.delivery.user.application.service;

import com.codefactory.delivery.user.application.dto.TokenInfo;

public interface TokenGenerateService {
    TokenInfo generate(String username, String password);
}
