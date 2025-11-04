package com.codefactory.delivery.store.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@ToString
@Getter
@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OperatingInfo {
    private LocalTime startHour;
    private LocalTime endHour;

    private List<DayOfWeek> weekdays;
}
