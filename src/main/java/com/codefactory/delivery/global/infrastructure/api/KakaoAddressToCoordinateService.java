package com.codefactory.delivery.global.infrastructure.api;

import com.codefactory.delivery.global.service.AddressToCoordinateService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KakaoAddressToCoordinateService implements AddressToCoordinateService {
    @Override
    public List<Double> getCoordinate(String address) {
        return List.of();
    }
}
