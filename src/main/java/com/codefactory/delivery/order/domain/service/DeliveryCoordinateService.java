package com.codefactory.delivery.order.domain.service;

import com.codefactory.delivery.global.service.AddressToCoordinateService;
import com.codefactory.delivery.order.domain.Order;
import com.codefactory.delivery.order.domain.OrderId;
import com.codefactory.delivery.order.domain.OrderRepository;
import com.codefactory.delivery.order.domain.exception.OrderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryCoordinateService {

    private final AddressToCoordinateService service;
    private final OrderRepository repository;

    @Transactional
    public void update(OrderId orderId, String address) {

        Order order = repository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        // 주소를 위도 경도 좌표로 변환
        List<Double> coords = service.getCoordinate(address);

        order.getDeliveryInfo().updateCoordinates(coords);
    }
}
