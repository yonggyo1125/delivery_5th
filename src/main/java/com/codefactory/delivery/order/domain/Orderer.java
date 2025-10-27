package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orderer {
    @AttributeOverrides(
            @AttributeOverride(name="id", column = @Column(name="orderer_id"))
    )
    private UserId userId;

    @Column(name="orderer_name")
    private String name;

    @Builder
    public Orderer(UserId userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
