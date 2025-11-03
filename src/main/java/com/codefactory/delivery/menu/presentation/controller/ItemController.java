package com.codefactory.delivery.menu.presentation.controller;

import com.codefactory.delivery.menu.application.service.ItemCreateService;
import com.codefactory.delivery.menu.application.service.ItemDeleteService;
import com.codefactory.delivery.menu.application.service.dto.ItemDto;
import com.codefactory.delivery.menu.presentation.dto.ItemRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/store/{storeId}")
@RequiredArgsConstructor
@Tag(name="메뉴 등록 API", description = "상점별 메뉴 등록, 수정, 삭제 기능 제공")
public class ItemController {
    private final ItemCreateService createService;
    private final ItemDeleteService deleteService;

    /**
     * 매장별 상품 등록
     *
     * @return
     */
    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@PathVariable("storeId") UUID storeId, @Valid @RequestBody ItemRequest req) {

        return createService.create(storeId, req);
    }

    /**
     * 상품 삭제
     * @param itemId
     */
    @DeleteMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable("itemId") UUID itemId) {
        deleteService.delete(itemId);
    }
}
