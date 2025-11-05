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

    @Column(length=45, name="orderer_name", nullable = false)
    private String name;

    @Column(length=65, name="orderer_email", nullable = false)
    private String email;

    @Builder
    protected Orderer(UserId id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
