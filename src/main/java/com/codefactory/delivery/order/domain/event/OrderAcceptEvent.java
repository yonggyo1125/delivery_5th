package com.codefactory.delivery.order.domain.event;

import com.codefactory.delivery.order.domain.OrderId;

// 주문 접수 이벤트
public record OrderAcceptEvent(
        OrderId orderId
) {}
