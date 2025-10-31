package com.codefactory.delivery.test1;

public class LoginService {
    public void process(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("이메일을 입력하세요.");
        }

        if (password == null || password.isBlank()) {
            throw new BadRequestException("비밀번호를 입력하세요.");
        }
    }
}
