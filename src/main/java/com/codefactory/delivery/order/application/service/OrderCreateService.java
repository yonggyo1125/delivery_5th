package com.codefactory.delivery.order.application.service;

import com.codefactory.delivery.order.application.service.dto.OrderInfoDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemDto;
import com.codefactory.delivery.order.domain.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCreateService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderId create(OrderInfoDto orderInfo, List<OrderItemDto> orderItems) {

        return null;
    }
}
