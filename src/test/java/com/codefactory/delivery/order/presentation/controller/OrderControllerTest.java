package com.codefactory.delivery.order.presentation.controller;

import com.codefactory.delivery.menu.domain.ItemStatus;
import com.codefactory.delivery.order.presentation.dto.CreateItemDto;
import com.codefactory.delivery.order.presentation.dto.CreateItemOptionDto;
import com.codefactory.delivery.order.presentation.dto.CreateRequest;
import com.codefactory.delivery.store.application.service.StoreCreateService;
import com.codefactory.delivery.store.application.service.StoreItemCreateService;
import com.codefactory.delivery.store.application.service.dto.ItemDto;
import com.codefactory.delivery.store.domain.Category;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.presentation.dto.CategoryDto;
import com.codefactory.delivery.store.presentation.dto.ItemOptionRequest;
import com.codefactory.delivery.store.presentation.dto.ItemRequest;
import com.codefactory.delivery.store.presentation.dto.StoreRequest;
import com.codefactory.delivery.user.test.MockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    StoreCreateService storeCreateService;

    @Autowired
    StoreItemCreateService itemCreateService;


    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    CreateRequest request;

    @BeforeEach
    @MockUser(roles = "OWNER")
    void init() {
        StoreRequest req = StoreRequest.builder()
                .storeName("테스트 상점")
                .storeTel("010-1000-1000")
                .storeAddress("상점 주소")
                .category(List.of(new CategoryDto(Category.KOREAN, true)))
                .build();
        StoreId storeId = storeCreateService.create(req);

        List<CreateItemDto> orderItems = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            List<ItemOptionRequest> options = List.of(
                    new ItemOptionRequest("옵션1", 1000),
                    new ItemOptionRequest("옵션2", 2000)
            );
            ItemRequest req2 = new ItemRequest(10000, "테스트 상품" + i, Category.KOREAN, ItemStatus.IN_STOCK, true, 100, options);
            ItemDto item = itemCreateService.create(storeId.getId(), req2);
            orderItems.add(new CreateItemDto(item.id(), 3, List.of(new CreateItemOptionDto(0, 1))));
        }

        request = CreateRequest.builder()
                .items(orderItems)
                .email("testuser@test.org")
                .memo("배송메모")
                .name("테스트 사용자")
                .address("테스트 주소")
                .build();
    }

    @Test
    @MockUser(roles = {"USER", "OWNER"})
    void orderCreateTest() throws Exception {
        String body = om.writeValueAsString(request);
        mockMvc.perform(post("/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print());
    }
}
