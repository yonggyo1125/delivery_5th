package com.codefactory.delivery.menu.application.service;

import com.codefactory.delivery.menu.application.service.dto.ItemDto;
import com.codefactory.delivery.menu.presentation.dto.ItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ItemCreateService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ItemDto create(UUID storeId, ItemRequest req) {
        return null;
    }
}
