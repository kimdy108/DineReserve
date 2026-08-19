package com.project.dine.reserve.dto.constant.member;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum MemberRole {
    MEMBER("사용자", "ROLE_MEMBER");

    private final String title;
    private final String value;

    MemberRole(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
