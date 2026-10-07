package com.project.dine.reserve.dto.constant.booking;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum BookingStatusType {
    BOOKING("예약"),
    CANCEL("취소"),
    COMPLETE("완료"),
    NO_SHOW("노쇼");

    private final String value;

    BookingStatusType(String value) {
        this.value = value;
    }
}
