package com.codefactory.delivery.menu.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ItemNotEditableException extends HttpStatusCodeException {
    public ItemNotEditableException() {
        super(HttpStatus.UNAUTHORIZED, "상품 수정/삭제권한이 없습니다.");
    }
}
