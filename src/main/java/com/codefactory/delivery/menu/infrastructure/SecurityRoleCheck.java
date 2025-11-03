package com.codefactory.delivery.menu.infrastructure;

import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.menu.domain.RoleCheck;
import com.codefactory.delivery.menu.infrastructure.persistence.ItemDetailsDao;
import com.codefactory.delivery.store.domain.StoreId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 상점별 메뉴 수정, 삭제 권한 체크
 */
@Service
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {

    private final ItemDetailsDao dao;


    @Override
    public boolean check(ItemId itemId) {
        Item item = dao.findById(itemId);
        if (item == null) return false;

        StoreId storeId = item.getStoreId();


        return false;
    }
}
