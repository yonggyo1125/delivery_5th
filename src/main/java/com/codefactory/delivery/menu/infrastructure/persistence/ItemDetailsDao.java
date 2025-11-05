package com.codefactory.delivery.menu.infrastructure.persistence;

import com.codefactory.delivery.menu.domain.Item;
import com.codefactory.delivery.menu.domain.ItemDetailsRepository;
import com.codefactory.delivery.menu.domain.ItemId;
import com.codefactory.delivery.menu.domain.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemDetailsDao implements ItemDetailsRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 메뉴 조회
     *
     * @param id
     * @return
     */
    @Override
    public Item findById(ItemId id) {
        QItem item = QItem.item;
        return queryFactory.selectFrom(item)
                .where(item.id.eq(id))
                .fetchFirst();
    }

    @Override
    public List<Item> findAllById(List<ItemId> ids) {
        QItem item = QItem.item;
        return queryFactory.selectFrom(item)
                .where(item.id.in(ids))
                .fetch();
    }
}
