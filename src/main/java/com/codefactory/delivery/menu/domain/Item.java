package com.codefactory.delivery.menu.domain;

import com.codefactory.delivery.global.infrastructure.persistence.BaseUserEntity;
import com.codefactory.delivery.global.infrastructure.persistence.converter.PriceConverter;
import com.codefactory.delivery.global.infrastructure.persistence.Price;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseUserEntity {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.UUID)
    private ItemId id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="P_ITEM_OPTION", joinColumns = @JoinColumn(name="item_id"))
    @OrderColumn(name="option_idx")
    private List<ItemOption> itemOptions;

    @Convert(converter = PriceConverter.class)
    private Price price;

    @Column(nullable = false)
    private String name;

    private boolean active; // 메뉴 노출 여부
}
