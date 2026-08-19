package com.project.dine.reserve.dto.constant.admin;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum AdminRole {
    ADMIN("관리자", "ROLE_ADMIN"),
    STORE_ADMIN("매장 관리자", "ROLE_STORE_ADMIN"),
    STORE_MANAGER("매장 매니저", "ROLE_STORE_MANAGER");

    private final String title;
    private final String value;

    AdminRole(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
