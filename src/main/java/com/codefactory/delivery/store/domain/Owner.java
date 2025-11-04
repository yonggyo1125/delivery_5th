package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.user.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner implements Serializable  {
    @AttributeOverrides(
            @AttributeOverride(name="id", column = @Column(name="owner_id"))
    )
    public UserId id;

    @Column(name="owner_name")
    public String name;
}
