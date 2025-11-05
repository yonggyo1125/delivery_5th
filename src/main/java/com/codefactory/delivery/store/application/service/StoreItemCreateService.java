package com.codefactory.delivery.store.application.service;

import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemOption;
import com.codefactory.delivery.menu.domain.ItemRepository;
import com.codefactory.delivery.menu.domain.Stock;
import com.codefactory.delivery.store.application.service.dto.ItemDto;
import com.codefactory.delivery.store.application.service.dto.ItemOptionDto;
import com.codefactory.delivery.store.domain.Store;
import com.codefactory.delivery.store.domain.StoreDetailsRepository;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.domain.StoreRepository;
import com.codefactory.delivery.store.presentation.dto.ItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreItemCreateService {

    private final StoreDetailsRepository storeDetailsRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
    public ItemDto create(UUID storeId, ItemRequest req) {
        StoreId id = StoreId.of(storeId);
        Store.exists(id, storeRepository);

        Store store = storeDetailsRepository.findById(id);

        List<ItemOption> options = req.itemOptions() == null ? null : req.itemOptions().stream()
                .map(o -> new ItemOption(o.optionName(), new Price(toInt(o.addPrice())))).toList();

        Item item = store.createItem(req.category(), new Price(toInt(req.price())), req.name(), req.status(), new Stock(toInt(req.stock())), options);

        itemRepository.save(item);

        List<ItemOptionDto> optionsDto = item.getItemOptions() == null ? null : item.getItemOptions().stream()
                .map(o -> new ItemOptionDto(o.getOptionName(), o.getAddPrice().getValue())).toList();

        return ItemDto.builder()
                .id(item.getId().getId())
                .price(item.getPrice().getValue())
                .name(item.getName())
                .createdAt(item.getCreatedAt())
                .outOfStock(item.isOutOfStock())
                .options(optionsDto)
                .build();
    }

    private int toInt(Integer num) {
        return num == null ? 0 : num;
    }
}
