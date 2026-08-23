package com.project.dine.reserve.dto.constant.error;

import com.project.dine.reserve.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    AUTH_FAIL("아이디 또는 비밀번호를 확인해주세요."),
    REFRESH_AUTH_FAIL("인증이 만료되었습니다."),
    NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다.");

    private final String message;
}
