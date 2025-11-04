package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.store.domain.exception.StoreNotFoundException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(length=100, nullable = false)
    private String storeName;

    @Column(length=45, nullable = false)
    private String storeTel;

    @Embedded
    private StoreAddress address;

    @Embedded
    private OperatingInfo operatingInfo;

    public static void exists(StoreId id, StoreRepository repository) {
        if (!repository.existsById(id)) {
            throw new StoreNotFoundException();
        }
    }

}
