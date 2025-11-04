package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.store.domain.exception.StoreNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * 1. 메뉴 생성은 매장에서 생성
 *     - OWNER, MASTER, MANAGER 권한이 있는 경우
 * 2. 메뉴 분류는 필수는 아님, 중복 분류는 안된다.
 */
@ToString
@Getter
@Entity
@Access(AccessType.FIELD)
@Table(name="P_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @EmbeddedId
    private StoreId id;

    @Embedded
    private Owner owner;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="P_STORE_CATEGORY", joinColumns = @JoinColumn(name="store_id"))
    @OrderColumn(name="category_idx")
    private List<StoreCategory> categories;

    @Column(length=100, nullable = false)
    private String storeName;

    @Column(length=45, nullable = false)
    private String storeTel;

    @Embedded
    private StoreAddress address;

    @Embedded
    private OperatingInfo operatingInfo;

    @Builder
    public Store(StoreId id, String storeName, String storeTel, StoreAddress address, OperatingInfo operatingInfo, List<StoreCategory> categories) {
        this.id = Objects.requireNonNullElse(id, StoreId.of());
        this.storeName = storeName;
        this.storeTel = storeTel;
        this.address = address;
        this.operatingInfo = operatingInfo;
        setCategories(categories);

    }

    private void setCategories(List<StoreCategory> categories) {
        if (categories == null || categories.isEmpty()) return;

        this.categories = categories.stream();
    }

    public static void exists(StoreId id, StoreRepository repository) {
        if (!repository.existsById(id)) {
            throw new StoreNotFoundException();
        }
    }

}
