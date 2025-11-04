package com.codefactory.delivery.menu.application.service;

import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.menu.domain.ItemRepository;
import com.codefactory.delivery.menu.domain.RoleCheck;
import com.codefactory.delivery.menu.domain.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemDeleteService {

    private final ItemRepository repository;
    private final RoleCheck roleCheck;
    @Transactional
    public void delete(UUID id) {

        ItemId itemId = ItemId.of(id);

        Item item = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        item.isEditable(roleCheck); // 삭제 권한 체크
        item.delete();
    }
}
