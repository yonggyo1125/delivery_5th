package com.codefactory.delivery.menu.application.service;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.menu.application.service.dto.ItemDto;
import com.codefactory.delivery.menu.application.service.dto.ItemOptionDto;
import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemOption;
import com.codefactory.delivery.menu.domain.ItemRepository;
import com.codefactory.delivery.menu.domain.Stock;
import com.codefactory.delivery.menu.presentation.dto.ItemRequest;
import com.codefactory.delivery.store.domain.Store;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemCreateService {

    private final ItemRepository repository;
    private final StoreRepository storeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ItemDto create(UUID storeId, ItemRequest req) {

        // 매장 등록 여부 체크
        Store.exists(StoreId.of(storeId), storeRepository);

        // 상품 옵션 변환
        List<ItemOption> options = req.itemOptions() == null ? null : req.itemOptions().stream().map(i -> new ItemOption(i.optionName(), new Price(i.addPrice() == null ? 0 : i.addPrice()))).toList();

        Item item = Item.builder()
                .name(req.name())
                .status(req.status())
                .price(new Price(req.price()))
                .storeId(new StoreId(storeId))
                .stock(new Stock(req.stock()))
                .itemOptions(options)
                .build();

        repository.save(item);

        List<ItemOptionDto> itemOptions = item.getItemOptions() == null ? null : item.getItemOptions().stream().map(i -> new ItemOptionDto(i.getOptionName(), i.getAddPrice().getValue())).toList();

        return ItemDto.builder()
                .id(item.getId().getId())
                .name(item.getName())
                .outOfStock(item.isOutOfStock())
                .price(item.getPrice().getValue())
                .createdAt(item.getCreatedAt())
                .options(itemOptions)
                .build();
    }
}
