package com.codefactory.delivery.store.application.service;

import com.codefactory.delivery.store.domain.*;
import com.codefactory.delivery.store.domain.service.StoreAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreUpdateService {

    private final RoleCheck roleCheck;
    private final StoreDetailsRepository detailsRepository;
    private final StoreRepository repository;
    private final StoreAddressService addressService;

    // 상점 일반 정보 수정
    public void updateInfo(UUID id, String storeName, String storeTel) {
        Store store = validateAndGet(id);

        store.changeInfo(storeName, storeTel);

        repository.save(store);
    }

    // 상점 운영 정보 수정
    public void updateOperatingInfo(UUID id, LocalTime startTime, LocalTime endTime, List<DayOfWeek> weekdays) {
        Store store = validateAndGet(id);
        store.changeOperatingInfo(startTime, endTime, weekdays);
    }

    // 상점 주소 정보 수정
    public void updateAddress(UUID id, String address) {
        Store store = validateAndGet(id);
        store.changeAddress(address, addressService);
    }

    private Store validateAndGet(UUID id) {
        StoreId storeId = StoreId.of(id);
        Store.exists(storeId, repository); // 상점이 등록되어 있는지 체크

        Store store = detailsRepository.findById(storeId);

        store.isEditable(roleCheck); // 상점 수정 권한이 있는지 체크

        return store;
    }
}
