package com.codefactory.delivery.user.presentation.controller;

import com.codefactory.delivery.global.presentation.exception.BadRequestException;
import com.codefactory.delivery.user.application.dto.TokenInfo;
import com.codefactory.delivery.user.application.dto.UserRegister;
import com.codefactory.delivery.user.application.dto.UserUpdate;
import com.codefactory.delivery.user.application.service.TokenGenerateService;
import com.codefactory.delivery.user.application.service.UserRegisterService;
import com.codefactory.delivery.user.application.service.UserUpdateService;
import com.codefactory.delivery.user.presentation.dto.*;
import com.codefactory.delivery.user.presentation.validator.UserRegisterValidator;
import com.codefactory.delivery.user.presentation.validator.UserUpdateValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/user/")
public class UserController {

    private final TokenGenerateService tokenService;
    private final UserRegisterService registerService;
    private final UserUpdateService updateService;

    // 토큰 발급
    @PostMapping("token")
    public TokenResponse generateToken(@Valid @RequestBody TokenRequest req) {
        TokenInfo tokenInfo = tokenService.generate(req.username(), req.password());

        return new TokenResponse(tokenInfo.access_token(),
                tokenInfo.expires_in(),
                tokenInfo.refresh_expires_in(),
                tokenInfo.refresh_token(),
                tokenInfo.token_type());
    }

    // 로그인한 사용자 정보 조회
    @GetMapping("profile")
    public UserResponse getProfile(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Map<String, Object> claims = jwt.getClaims();
        String name = claims.getOrDefault("family_name", "") + (String)claims.getOrDefault("given_name", "");

        return new UserResponse(userId,
                (String)claims.getOrDefault("preferred_username", ""),
                (String)claims.getOrDefault("email", ""),
                name,
                (String)claims.getOrDefault("mobile", ""));
    }

    // 회원 가입
    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody UserRegisterRequest req) {

        new UserRegisterValidator().validate(req); // 추가 검증 처리

        UserRegister dto = UserRegister.builder()
                .username(req.username())
                .password(req.password())
                .email(req.email())
                .firstName(req.firstName())
                .lastName(req.lastName())
                .mobile(req.mobile())
                .build();
        registerService.register(dto);
    }

    // 회원정보 수정
    @PatchMapping("profile")
    public void updateProfile(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody UserUpdateRequest req) {

        new UserUpdateValidator().validateUpdateProfile(req); // 추가 검증 처리

        UUID userId = UUID.fromString(jwt.getSubject());
        UserUpdate dto = UserUpdate
                .builder()
                .email(req.email())
                .firstName(req.firstName())
                .lastName(req.lastName())
                .mobile(req.mobile())
                .build();
        updateService.update(userId, dto);
    }

    // 비밀번호 변경
    @PatchMapping("password")
    public void changePassword(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody PasswordChangeRequest req) {
        new UserUpdateValidator().validateChangePassword(req); // 추가 검증 처리

        updateService.updatePassword(UUID.fromString(jwt.getSubject()), req.password());
    }

    // 새 Role 부여
    @PatchMapping("role")
    public void changeRole(@AuthenticationPrincipal Jwt jwt, @RequestBody List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new BadRequestException("변경할 ROLE을 전송해 주세요.");
        }

        updateService.updateUserRole(UUID.fromString(jwt.getSubject()), roles);
    }
}