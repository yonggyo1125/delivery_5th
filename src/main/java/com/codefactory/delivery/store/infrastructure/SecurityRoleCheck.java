package com.codefactory.delivery.store.infrastructure;

import com.codefactory.delivery.store.domain.RoleCheck;
import com.codefactory.delivery.store.domain.Store;
import com.codefactory.delivery.store.domain.StoreId;
import com.codefactory.delivery.store.infrastructure.persistence.StoreDetailsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {

    private final StoreDetailsDao storeDao;

    @Override
    public boolean check(StoreId id) {
        Store store = storeDao.findById(id);
        if (store == null) return false;

        // OWNER(상점 주인과 동일), MASTER, MANAGER
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            UUID userId = UUID.fromString(jwt.getSubject());

        }

        return false;
    }
}
