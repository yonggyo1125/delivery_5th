package com.codefactory.delivery.order.domain.event;

import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.order.domain.OrderId;

import java.util.List;
import java.util.Map;

// 주문 접수 이벤트
public record OrderAcceptEvent(
        OrderId orderId,
        List<Map<ItemId, Integer>> purchaseCounts
) {}
