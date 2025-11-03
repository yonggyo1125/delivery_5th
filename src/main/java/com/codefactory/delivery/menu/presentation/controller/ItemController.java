package com.codefactory.delivery.menu.presentation.controller;

import com.codefactory.delivery.menu.application.service.ItemCreateService;
import com.codefactory.delivery.menu.application.service.dto.ItemDto;
import com.codefactory.delivery.menu.presentation.dto.ItemRequest;
import com.codefactory.delivery.menu.presentation.dto.ItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@PathVariable("storeId") UUID storeId, @Valid @RequestBody ItemRequest req) {

        ItemDto itemDto = createService.create(storeId, req);

        return null;
    }
}
