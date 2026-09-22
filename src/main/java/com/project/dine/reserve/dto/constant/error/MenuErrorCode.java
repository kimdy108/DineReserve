package com.project.dine.reserve.dto.constant.error;

import com.project.dine.reserve.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {
    NO_MENU_CATEGORY("존재하지 않는 메뉴 카테고리입니다."),
    EXIST_MENU_CATEGORY("이미 존재하는 메뉴 카테고리입니다."),
    NO_MENU_INFO("존재하지 않는 메뉴입니다.");

    private final String message;
}
