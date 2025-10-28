package com.codefactory.delivery.order.infrastructure.persistence;

import com.codefactory.delivery.order.domain.Order;
import com.codefactory.delivery.order.domain.OrderDetailsRepository;
import com.codefactory.delivery.order.infrastructure.persistence.dto.OrderSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailsDao implements OrderDetailsRepository {
    @Override
    public Page<Order> findAll(OrderSearch search, int page, int size) {
        return null;
    }
}
