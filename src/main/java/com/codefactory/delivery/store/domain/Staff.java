package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff {
    @Column(name="staff_id")
    private UserId id;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id.getId(), staff.id.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id.getId());
    }
}
