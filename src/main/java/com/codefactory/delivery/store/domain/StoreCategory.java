package com.codefactory.delivery.store.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategory {
    @Embedded
    private StoreId storeId;

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private Category category;

    private boolean active; // true : 분류 노출, false : 미노출

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StoreCategory that = (StoreCategory) o;
        return category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(category);
    }
}
