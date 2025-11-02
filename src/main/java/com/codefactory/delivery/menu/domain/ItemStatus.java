package com.codefactory.delivery.menu.domain;

public enum ItemStatus {
    IN_STOCK, // 재고 있음
    OUT_OF_STOCK, // 재고 없음(강제 품절 처리)
    UNLIMITED_STOCK // 재고 사용 없이 판매 가능
}
