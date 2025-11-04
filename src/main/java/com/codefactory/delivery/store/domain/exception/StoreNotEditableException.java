package com.codefactory.delivery.store.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class StoreNotEditableException extends HttpStatusCodeException {
    public StoreNotEditableException() {
        super(HttpStatus.UNAUTHORIZED, "상점의 수정/삭제 권한이 없습니다.");
    }
}
