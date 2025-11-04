package com.codefactory.delivery.store.infrastructure;

import com.codefactory.delivery.store.domain.RoleCheck;
import com.codefactory.delivery.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {

    // 상점의 수정,삭제 권한
    @Override
    public boolean check(Store store) {
        if (store == null) return false;

        String role = SecurityRoleCheckHelper.getRole(store);

        return !StringUtils.hasText(role); // 문자열이 없는 경우는 ADMIN, OWNER, STAFF 모두 아닌 경우
    }

}
