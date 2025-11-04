package com.codefactory.delivery.menu.infrastructure;

import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.menu.domain.RoleCheck;
import com.codefactory.delivery.menu.infrastructure.persistence.ItemDetailsDao;
import com.codefactory.delivery.store.domain.Store;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.infrastructure.persistence.StoreDetailsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 상점별 메뉴 수정, 삭제 권한 체크
 */
@Service
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {

    private final ItemDetailsDao dao;
    private final StoreDetailsDao storeDao;

    @Override
    public boolean check(ItemId itemId) {
        Item item = dao.findById(itemId);
        if (item == null) return false;

        StoreId storeId = item.getStoreId();
        Store store = storeDao.findById(storeId);
        if (store == null) return false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            boolean isAdmin = auth.getAuthorities().stream().anyMatch(s -> List.of("ROLE_MANAGER", "ROLE_MASTER").contains(s.getAuthority()));

            UUID userId  = UUID.fromString(jwt.getSubject());

            return isAdmin || userId.equals(store.getOwner().id.getId());
        }
        return false;
    }
}
