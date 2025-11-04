package com.codefactory.delivery.store.infrastructure.api;

import com.codefactory.delivery.store.domain.service.StoreAddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class KakaoStoreAddressClientTest {
    @Autowired
    StoreAddressService service;

    @Test
    @DisplayName("주소를 위도 경도로 변환 테스트")
    void addressToCoordinateTest() {
        service.getCoordinate("인천광역시 계양구 임학안로 28번길 10");

    }
}
