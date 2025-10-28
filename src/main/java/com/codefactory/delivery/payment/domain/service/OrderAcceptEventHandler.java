package com.codefactory.delivery.payment.domain.service;

import com.codefactory.delivery.order.domain.event.OrderAcceptEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class OrderAcceptEventHandler {

    @Async
    @TransactionalEventListener(OrderAcceptEvent.class)
    public void handle(OrderAcceptEvent event) {
        // 주문 접수 후 결제 진행 도메인 로직 추가

    }
}
