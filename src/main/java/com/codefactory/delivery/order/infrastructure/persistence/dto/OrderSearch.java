package com.codefactory.delivery.order.infrastructure.persistence.dto;

import com.codefactory.delivery.order.domain.OrderId;
import com.codefactory.delivery.order.domain.Orderer;

public record OrderSearch(
        OrderId orderId,
        Orderer orderer,
        String sopt,
        String skey
) {}
