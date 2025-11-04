package com.codefactory.delivery.menu.domain;

import com.codefactory.delivery.global.infrastructure.persistence.BaseUserEntity;
import com.codefactory.delivery.global.infrastructure.persistence.Price;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import com.codefactory.delivery.menu.domain.exception.ItemNotEditableException;
import com.codefactory.delivery.menu.domain.exception.ItemNotFoundException;
import com.codefactory.delivery.menu.infrastructure.persistence.converter.StockConverter;
import com.codefactory.delivery.store.domain.StoreId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ToString
@Getter
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseUserEntity {
    @EmbeddedId
    private ItemId id;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="id", column = @Column(name="store_id", nullable = false))
    )
    private StoreId storeId; // 상점 ID

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="P_ITEM_OPTION", joinColumns = @JoinColumn(name="item_id"))
    @OrderColumn(name="option_idx")
    private List<ItemOption> itemOptions;

    @Convert(converter = PriceConverter.class)
    private Price price;

    @Column(length=100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length=30)
    private ItemStatus status;

    private boolean active; // 메뉴 노출 여부

    @Convert(converter = StockConverter.class)
    @Column(name="stock")
    private Stock stock;

    @Transient
    private boolean outOfStock; // 품절 상태 여부

    @Builder
    public Item(StoreId storeId, ItemId id, Price price, String name, ItemStatus status, Stock stock, List<ItemOption> itemOptions) {
        this.storeId = storeId;
        this.id = Objects.requireNonNullElse(id, ItemId.of());
        this.price = price;
        this.name = name;
        this.itemOptions = itemOptions;
        this.status = Objects.requireNonNullElse(status, ItemStatus.UNLIMITED_STOCK); // 재고 기본 값은 무제한 재고
        setStock(stock);

    }

    // 재고 등록
    private void setStock(Stock stock) {
        this.stock = stock;
        // 품절 상태 체크
        if (stock.getValue() == 0 || status == ItemStatus.OUT_OF_STOCK) {
            outOfStock = true;
        }
    }

    // 옵션 추가
    public void addOption(ItemOption itemOption) {
        this.itemOptions = Objects.requireNonNullElseGet(this.itemOptions, ArrayList::new);
        this.itemOptions.add(itemOption);
    }

    // 옵션 제거
    public void removeOption(ItemOption itemOption) {
        if (itemOptions == null) return;

        itemOptions.remove(itemOption);
    }

    // 옵션 순서 번호로 제거
    public void removeOption(int idx) {
        if (itemOptions == null) return;

        itemOptions.remove(idx);
    }

    // 전체 옵션 제거
    public void removeOptionAll() {
        if (itemOptions == null) return;

        itemOptions.forEach(o -> itemOptions.remove(o));
    }

    /**
     * 상품 수량 + 옵션 번호, 수량으로 금액 계산
     * @param itemCnt
     * @param options
     * @return
     */
    public Price getTotal(int itemCnt, Map<Integer, Integer> options) {
        Price totalPrice = price.add(price.multiply(itemCnt));

        if (itemOptions != null && options != null) {
            options.forEach((optionIdx, optionCnt) -> {
                ItemOption item = itemOptions.get(optionIdx);
                if (item == null) return;
                totalPrice.add(item.getAddPrice().multiply(optionCnt));
            });
        }

        return totalPrice;
    }

    // 상품 삭제 처리
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    // 상품 등록 여부 체크
    public static void exists(ItemId id, ItemRepository repository) {
        if (!repository.existsById(id)) {
            throw new ItemNotFoundException();
        }
    }

    // 상품 수정 가능 여부
    public void isEditable(RoleCheck roleCheck) {
        if (!roleCheck.check(id)) {
            throw new ItemNotEditableException();
        }
    }
}
