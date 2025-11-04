package com.codefactory.delivery.store.domain;

import com.codefactory.delivery.store.infrastructure.persistence.converter.DayOfWeekConverter;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Convert;
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

    @Convert(converter = DayOfWeekConverter.class)
    private List<DayOfWeek> weekdays;
}
