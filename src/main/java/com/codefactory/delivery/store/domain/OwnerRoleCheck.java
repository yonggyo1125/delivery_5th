package com.codefactory.delivery.store.domain;

import java.util.Collection;

public interface OwnerRoleCheck {
    boolean check(Collection<Staff> staffs);
}
