package com.codefactory.delivery.store.application.service;

import com.codefactory.delivery.store.domain.*;
import com.codefactory.delivery.store.presentation.dto.StoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreUpdateService {

    private final RoleCheck roleCheck;
    private final StoreDetailsRepository detailsRepository;
    private final StoreRepository repository;

    @Transactional
    public void update(UUID id, StoreRequest req) {
        StoreId storeId = StoreId.of(id);
        Store.exists(storeId, repository); // 상점이 등록되어 있는지 체크

        Store store = detailsRepository.findById(storeId);

        store.isEditable(roleCheck); // 상점 수정 권한이 있는지 체크



    }
}
