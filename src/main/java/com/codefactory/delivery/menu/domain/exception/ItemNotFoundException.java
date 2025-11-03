package com.codefactory.delivery.menu.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ItemNotFoundException extends HttpStatusCodeException {
    public ItemNotFoundException() {
        super(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다.");
    }
}
