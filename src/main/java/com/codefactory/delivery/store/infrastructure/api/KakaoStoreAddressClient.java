package com.codefactory.delivery.store.infrastructure.api;

import com.codefactory.delivery.store.domain.service.StoreAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KakaoStoreAddressClient implements StoreAddressService {
    @Override
    public List<Double> getCoordinate(String address) {
        return List.of(0.0, 0.0);
    }
}
