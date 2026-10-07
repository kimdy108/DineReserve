package com.project.dine.reserve.dto.constant.booking;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum BookingType {
    ADMIN("관리자"),
    MEMBER("회원"),
    GUEST("비회원"),
    NONE("없음");

    private final String value;

    BookingType(String value) {
        this.value = value;
    }
}
