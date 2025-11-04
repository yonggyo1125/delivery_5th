package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.global.infrastructure.persistence.BaseUserEntity;
import com.codefactory.delivery.store.domain.exception.StoreNotEditableException;
import com.codefactory.delivery.store.domain.exception.StoreNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 1. 메뉴 생성은 매장에서 생성
 *     - OWNER, MASTER, MANAGER 권한이 있는 경우
 * 2. 메뉴 분류는 필수는 아님, 중복 분류는 안된다.
 * 3. 매장의 삭제는 지난 주문 내역 및 메뉴를 유지하기 위해서 소프트 삭제만 허용
 * 4. 삭제, 수정 권한은 OWNER(같은 상점 주인만 삭제), MASTER, MANAGER 권한이 있는 경우
 * 5. 상점 분류의 추가, 삭제
 */
@ToString
@Getter
@Entity
@Access(AccessType.FIELD)
@Table(name="P_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseUserEntity {
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

        this.categories = categories.stream().distinct().toList();
    }


    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    /**
     *  삭제, 수정 권한
     */
    public void isEditable(RoleCheck roleCheck) {
        if (!roleCheck.check(id)) {
            // 권한이 없는 경우
            throw new StoreNotEditableException();
        }
    }

    public void addCategory(Category category, boolean active) {
        categories = Objects.requireNonNullElseGet(categories, ArrayList::new);
        categories.add(new StoreCategory(id, category, active));
        categories = categories.stream().distinct().toList();
    }


    public void removeCategory(Category category) {
        removeCategory(List.of(category));
    }

    public void removeCategory(List<Category> categories) {
        if (this.categories == null || categories.isEmpty()) return;

        this.categories = this.categories.stream().filter(c -> !categories.contains(c.getCategory())).toList();
    }

    public static void exists(StoreId id, StoreRepository repository) {
        if (!repository.existsById(id)) {
            throw new StoreNotFoundException();
        }
    }

}
