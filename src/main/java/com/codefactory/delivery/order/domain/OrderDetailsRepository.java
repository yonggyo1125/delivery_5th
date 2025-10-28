package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.order.infrastructure.persistence.dto.OrderSearch;
import org.springframework.data.domain.Page;

public interface OrderDetailsRepository {
    Page<Order> findAll(OrderSearch search, int page, int size);
}