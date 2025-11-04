package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff {
    @Column(name="staff_id")
    private UserId id;
}
