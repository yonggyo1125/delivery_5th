package com.codefactory.delivery.store.application.service;

import com.codefactory.delivery.store.domain.Category;
import com.codefactory.delivery.store.domain.Store;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.domain.StoreRepository;
import com.codefactory.delivery.store.presentation.dto.CategoryDto;
import com.codefactory.delivery.store.presentation.dto.CreateRequest;
import com.codefactory.delivery.user.test.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
public class StoreServiceTest {
    @Autowired
    StoreCreateService createService;

    @Autowired
    StoreRepository repository;

    CreateRequest request;

    @BeforeEach
    void init() {
        request = CreateRequest.builder()
                .storeName("테스트 매장")
                .storeAddress("테스트 주소")
                .storeTel("02-100-1000")
                .startHour(LocalTime.of(10, 0))
                .endHour(LocalTime.of(19, 0))
                .weekdays(List.of(MONDAY,TUESDAY, WEDNESDAY))
                .category(List.of(new CategoryDto(Category.KOREAN, true), new CategoryDto(Category.CHINESE, true)))
                .build();
    }

    @Test
    @Transactional
    @DisplayName("상점 등록 테스트")
    @MockUser(roles = "OWNER")
    void createStoreTest() {

        assertDoesNotThrow(() -> {
            StoreId storeId = createService.create(request);

            Store store = repository.findById(storeId).orElseThrow();
            System.out.println(store);
        });
    }
}
