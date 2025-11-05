package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.store.domain.dto.StoreSearch;

import java.util.List;

public interface StoreDetailsRepository {
        Store findById(StoreId storeId);
        List<Store> findAll(StoreSearch search, int page, int size);
}
