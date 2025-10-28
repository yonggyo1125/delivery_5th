package com.codefactory.delivery.order.domain;

import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orderer {
    @Column(name="orderer_id")
    private UserId id;

    @Column(name="orderer_name")
    private String name;

    @Builder
    public Orderer(UserId id, String name) {
        this.id = id;
        this.name = name;
    }
}
