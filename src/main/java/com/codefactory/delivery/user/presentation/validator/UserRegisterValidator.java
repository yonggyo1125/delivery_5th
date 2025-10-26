package com.codefactory.delivery.user.presentation.validator;

import com.codefactory.delivery.global.presentation.exception.BadRequestException;
import com.codefactory.delivery.user.presentation.dto.UserRegisterRequest;
import org.springframework.util.StringUtils;

// 회원가입 추가 검증
public class UserRegisterValidator implements PasswordValidator, MobileValidator {
    public void validate(UserRegisterRequest req) {

        String password = req.password();
        String confirmPassword = req.confirmPassword();

        // 비밀번호 복잡성 체크
        if (!checkAlpha(password, false) || !checkNumber(password) || !checkSpecialChars(password)) {
            throw new BadRequestException("password", "비밀번호는 알파벳 대소문자, 숫자, 특수 문자 포함 8자리 이상 입력하세요. ");
        }

        // 비밀번호, 비밀번호 확인 일치 여부
        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("confirmPassword", "비밀번호가 일치하지 않습니다.");
        }

        // 휴대전화번호 체크
        String mobile = req.mobile();
        if (StringUtils.hasText(mobile) && !checkMobile(mobile)) {
            throw new BadRequestException("mobile", "휴대전화 번호 형식이 아닙니다.");
        }
    }
}
