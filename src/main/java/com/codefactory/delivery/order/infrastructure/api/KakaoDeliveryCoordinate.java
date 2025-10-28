package com.codefactory.delivery.order.infrastructure.api;

import com.codefactory.delivery.order.domain.service.DeliveryCoordinate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KakaoDeliveryCoordinate implements DeliveryCoordinate {
    @Override
    public List<Double> getCoordinate(String address) {
        return List.of();
    }
}
