package com.codefactory.delivery.menu.presentation.controller;

import com.codefactory.delivery.menu.application.service.ItemCreateService;
import com.codefactory.delivery.menu.presentation.dto.ItemRequest;
import com.codefactory.delivery.menu.presentation.dto.ItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store/{storeId}")
@RequiredArgsConstructor
public class ItemController {
    private final ItemCreateService createService;

    /**
     * 매장별 상품 등록
     *
     * @return
     */
    @PostMapping("/item")
    public ItemResponse createItem(@Valid @RequestBody ItemRequest req) {

        createService.create(req);

        return null;
    }
}
