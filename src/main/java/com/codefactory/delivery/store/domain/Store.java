package com.codefactory.delivery.store.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.UUID)
    private StoreId id;

    @Embedded
    private Owner owner;



}
