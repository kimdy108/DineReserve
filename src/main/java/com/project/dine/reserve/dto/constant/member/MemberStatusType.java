package com.project.dine.reserve.dto.constant.member;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum MemberStatusType {
    ACTIVE("활성"),
    SUSPENDED("정지");

    private final String value;

    MemberStatusType(String value) {
        this.value = value;
    }
}
