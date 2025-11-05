package com.codefactory.delivery.menu.domain;

import java.util.List;

public interface ItemDetailsRepository {
    Item findById(ItemId id);
    List<Item> findAllById(List<ItemId> ids);
}
