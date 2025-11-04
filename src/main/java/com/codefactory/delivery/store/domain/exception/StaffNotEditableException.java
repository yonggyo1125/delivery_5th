package com.codefactory.delivery.store.domain.exception;

public class StaffNotEditableException extends StoreNotEditableException {
    public StaffNotEditableException() {
        super("직원의 추가/제거 권한이 없습니다.");
    }
}
