package com.project.dine.reserve.dto.constant.error;

import com.project.dine.reserve.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    NO_MEMBER("존재하지 않는 사용자입니다."),
    EXIST_ID("이미 사용중인 아이디입니다.");

    private final String message;
}
