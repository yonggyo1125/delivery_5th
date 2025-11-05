package com.codefactory.delivery.order.application.service;

import com.codefactory.delivery.menu.domain.ItemStatus;
import com.codefactory.delivery.order.application.service.dto.OrderInfoDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemDto;
import com.codefactory.delivery.order.application.service.dto.OrderItemOptionDto;
import com.codefactory.delivery.order.domain.Order;
import com.codefactory.delivery.order.domain.OrderId;
import com.codefactory.delivery.order.domain.OrderRepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class OrderCreateServiceTest {

    @Autowired
    StoreCreateService storeCreateService;

    @Autowired
    StoreItemCreateService itemCreateService;

    @Autowired
    OrderCreateService orderCreateService;

    @Autowired
    OrderRepository orderRepository;

    List<UUID> itemIds;

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

        itemIds = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            List<ItemOptionRequest> options = List.of(
                    new ItemOptionRequest("옵션1", 1000),
                    new ItemOptionRequest("옵션2", 2000)
            );
            ItemRequest req2 = new ItemRequest(10000, "테스트 상품" + i, Category.KOREAN, ItemStatus.IN_STOCK, true, 100, options);
            ItemDto item = itemCreateService.create(storeId.getId(), req2);
            itemIds.add(item.id());
        }
    }


    @Test
    @MockUser(roles={"USER", "OWNER"})
    @DisplayName("주문 등록 테스트")
    @Transactional
    void orderCreateTest() {
        Jwt jwt =  (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = UUID.fromString(jwt.getSubject());

        List<OrderItemDto> items = itemIds.stream().map(id -> new OrderItemDto(id, 2, List.of(new OrderItemOptionDto(0, 3)))).toList();

        OrderInfoDto orderInfo = new OrderInfoDto(userId, "테스트 주문자", "testuser@test.org", "배송지 주소", "배송 메모");
        OrderId orderId = orderCreateService.create(orderInfo, items);

        Order createdOrder = orderRepository.findById(orderId).orElse(null);
        System.out.println(createdOrder);
    }
}
