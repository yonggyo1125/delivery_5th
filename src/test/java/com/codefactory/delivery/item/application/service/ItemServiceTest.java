package com.codefactory.delivery.item.application.service;

import com.codefactory.delivery.menu.application.service.ItemCreateService;
import com.codefactory.delivery.menu.domain.ItemStatus;
import com.codefactory.delivery.menu.presentation.dto.ItemOptionRequest;
import com.codefactory.delivery.menu.presentation.dto.ItemRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@DisplayName("메뉴 기능 테스트")
@ActiveProfiles("test")
public class ItemServiceTest {

    @Autowired
    ItemCreateService createService;

    @Test
    @DisplayName("메뉴 생성 테스트")
    void createTest() {
        List<ItemOptionRequest> options = IntStream.rangeClosed(1, 5).mapToObj(i -> new ItemOptionRequest("옵션" + i, 1000 * i)).toList();
        ItemRequest req = new ItemRequest(1000, "메뉴1", ItemStatus.UNLIMITED_STOCK, true, 0, options);
        createService.create(UUID.randomUUID(), req);

    }
}
