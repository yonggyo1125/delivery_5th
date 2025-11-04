package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.global.infrastructure.persistence.BaseUserEntity;
import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.menu.domain.*;
import com.codefactory.delivery.store.domain.exception.CategoryNotFoundException;
import com.codefactory.delivery.store.domain.exception.StoreNotEditableException;
import com.codefactory.delivery.store.domain.exception.StoreNotFoundException;
import com.codefactory.delivery.store.infrastructure.persistence.converter.StaffConverter;
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
 * 6. 상점을 통해서만 상품을 만든다.
 * 7. 사장님외에도 직원이 매장을 관리 할수 있다.
 *      - 사장이 직원을 추가, 제거
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

    @Convert(converter = StaffConverter.class)
    private List<Staff> staffs; // 직원들

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
        categories.add(new StoreCategory(category, active));
        categories = categories.stream().distinct().toList();
    }


    public void removeCategory(Category category) {
        removeCategory(List.of(category));
    }

    public void removeCategory(List<Category> categories) {
        if (this.categories == null || categories.isEmpty()) return;

        this.categories = this.categories.stream().filter(c -> !categories.contains(c.getCategory())).toList();
    }

    public boolean categoryExists(Category category) {
        return categories != null && categories.stream().anyMatch(c -> c.getCategory() == category);
    }

    // 상점 -> 상품 생성
    public Item createItem(Category category, Price price, String name, ItemStatus status, Stock stock, List<ItemOption> itemOptions) {
        // 카테고리가 실제로 등록되어 있는지 체크
        if (category != null && !categoryExists(category)) {
            throw new CategoryNotFoundException();
        }

        return Item.builder()
                .storeId(id)
                .price(price)
                .name(name)
                .status(status)
                .stock(stock)
                .itemOptions(itemOptions)
                .build();
    }

    /**
     * 직원 추가
     *  OWNER권한만 추가가능, 가능한 회원은 STAFF 권한이 있어야 한다.
     * @param staffs
     */
    public void addStaff(List<Staff> staffs, OwnerRoleCheck roleCheck) {

    }

    public void addStaff(Staff staff, OwnerRoleCheck roleCheck) {
        addStaff(List.of(staff), roleCheck);
    }

    /**
     * 직원 제거
     *
     * @param staffs
     */
    public void removeStaff(List<Staff> staffs, OwnerRoleCheck roleCheck) {

    }

    public void removeStaff(Staff staff, OwnerRoleCheck roleCheck) {
        removeStaff(List.of(staff), roleCheck);
    }


    public static void exists(StoreId id, StoreRepository repository) {
        if (!repository.existsById(id)) {
            throw new StoreNotFoundException();
        }
    }

}
