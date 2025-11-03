package com.codefactory.delivery.store.infrastructure.persistence;

import com.codefactory.delivery.store.domain.QStore;
import com.codefactory.delivery.store.domain.Store;
import com.codefactory.delivery.store.domain.StoreDetailsRepository;
import com.codefactory.delivery.store.domain.StoreId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreDetailsDao implements StoreDetailsRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 상점 하나 조회
     *
     * @param storeId
     * @return
     */
    @Override
    public Store findById(StoreId storeId) {
        QStore store = QStore.store;
        return queryFactory.selectFrom(store)
                .where(store.id.eq(storeId))
                .fetchFirst();
    }
}
