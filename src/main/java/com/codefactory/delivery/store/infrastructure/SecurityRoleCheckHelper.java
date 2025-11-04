package com.codefactory.delivery.store.infrastructure;

import com.codefactory.delivery.store.domain.Store;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public class SecurityRoleCheckHelper {
    public static String getRole(Store store) {
        // OWNER(상점 주인과 동일), MASTER, MANAGER
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            UUID userId = UUID.fromString(jwt.getSubject());

            boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> List.of("ROLE_MANAGER", "ROLE_MASTER").contains(a.getAuthority())); // 관리자
            if (isAdmin) return "ADMIN";

            boolean isOwner = userId.equals(store.getOwner().id.getId()); // 가게 주인인지
            if (isOwner) return "OWNER";

            // 직원도 수정, 삭제 권한이 있음
            boolean isStaff = store.getStaffs() == null ? false : store.getStaffs().stream().anyMatch(s -> s.getId().getId().equals(userId));
            if (isStaff) return "STAFF";
        }

        return "";
    }
}
