package com.codefactory.delivery.menu.domain.service;

import com.codefactory.delivery.order.domain.event.OrderAcceptEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ItemCountsEventHandler {
    @Async
    @TransactionalEventListener(OrderAcceptEvent.class)
    public void handle(OrderAcceptEvent event) {
        // 메뉴별 주문 통계 업데이트

    }
}
