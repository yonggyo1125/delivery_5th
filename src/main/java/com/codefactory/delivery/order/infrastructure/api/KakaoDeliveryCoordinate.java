package com.codefactory.delivery.order.infrastructure.api;

import com.codefactory.delivery.order.domain.service.DeliveryCoordinate;

import java.util.List;

public class KakaoDeliveryCoordinate implements DeliveryCoordinate {
    @Override
    public List<Double> getCoordinate(String address) {
        return List.of();
    }
}
