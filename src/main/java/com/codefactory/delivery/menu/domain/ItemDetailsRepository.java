package com.codefactory.delivery.menu.domain;

public interface ItemDetailsRepository {
    Item findById(ItemId id);
}
