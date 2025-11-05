package com.codefactory.delivery.payment.domain.service;

import com.codefactory.delivery.order.domain.event.OrderRefundEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class OrderRefundEventHandler {

    @Async
    @TransactionalEventListener(OrderRefundEvent.class)
    public void handler(OrderRefundEvent event) {
        // 결제 취소....

    }
}
