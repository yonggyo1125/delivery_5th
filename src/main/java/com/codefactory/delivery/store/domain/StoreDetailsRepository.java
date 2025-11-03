package com.codefactory.delivery.store.domain;

public interface StoreDetailsRepository {
        Store findById(StoreId storeId);
}
