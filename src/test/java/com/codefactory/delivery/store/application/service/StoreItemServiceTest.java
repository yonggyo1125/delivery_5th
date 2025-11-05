package com.codefactory.delivery.store.application.service;

import com.codefactory.delivery.menu.domain.ItemStatus;
import com.codefactory.delivery.store.application.service.dto.ItemDto;
import com.codefactory.delivery.store.domain.Category;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.presentation.dto.CategoryDto;
import com.codefactory.delivery.store.presentation.dto.ItemOptionRequest;
import com.codefactory.delivery.store.presentation.dto.ItemRequest;
import com.codefactory.delivery.store.presentation.dto.StoreRequest;
import com.codefactory.delivery.user.test.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class StoreItemServiceTest {
    @Autowired
    StoreItemCreateService createService;

    @Autowired
    StoreCreateService storeCreateService;

    StoreId storeId;

    @BeforeEach
    @MockUser(roles = "OWNER")
    void init() {
        StoreRequest req = StoreRequest.builder()
                .storeName("테스트 상점")
                .storeTel("010-1000-1000")
                .storeAddress("상점 주소")
                .category(List.of(new CategoryDto(Category.KOREAN, true)))
                .build();
        storeId = storeCreateService.create(req);
    }

    @Test
    @MockUser(roles = "OWNER")
    @DisplayName("상품 등록 테스트")
    void itemCreateTest() {
        List<ItemOptionRequest> options = List.of(
                new ItemOptionRequest("옵션1", 1000),
                new ItemOptionRequest("옵션2", 2000)
        );
        ItemRequest req = new ItemRequest(10000, "테스트 상품", Category.KOREAN, ItemStatus.IN_STOCK, true, 100, options);
        ItemDto item = createService.create(storeId.getId(), req);
        System.out.println(item);
    }
}
