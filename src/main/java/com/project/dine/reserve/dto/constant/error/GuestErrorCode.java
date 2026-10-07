package com.project.dine.reserve.dto.constant.error;

import com.project.dine.reserve.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GuestErrorCode implements ErrorCode {
    NO_GUEST("존재하지 않는 비회원 사용자입니다.");

    private final String message;
}
