package com.codefactory.delivery.order.domain.cart;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class CartItemNotFoundException extends HttpStatusCodeException {
    public CartItemNotFoundException() {
        super(HttpStatus.NOT_FOUND, "장바구니에 상품을 담아주세요.");
    }
}
