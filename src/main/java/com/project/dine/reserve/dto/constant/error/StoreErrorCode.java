package com.project.dine.reserve.dto.constant.error;

import com.project.dine.reserve.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {
    NO_STORE_CATEGORY("존재하지 않는 카테고리입니다."),
    EXIST_STORE_CATEGORY("이미 존재하는 카테고리입니다."),
    USE_CATEGORY("사용 중인 카테고리입니다."),
    NO_STORE_INFO("존재하지 않는 매장입니다."),
    EXIST_STORE_INFO("이미 존재하는 매장입니다.");

    private final String message;
}
