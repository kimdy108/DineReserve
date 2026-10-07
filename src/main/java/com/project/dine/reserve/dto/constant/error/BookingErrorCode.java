package com.project.dine.reserve.dto.constant.error;

import com.project.dine.reserve.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingErrorCode implements ErrorCode {
    NO_BOOKING("존재하지 않는 예약입니다."),
    NOT_VALID_UPDATE("수정할 수 있는 권한이 없습니다"),
    NOT_VALID_CANCEL("취소할 수 있는 권한이 없습니다"),
    NOT_VALID_DELETE("삭제할 수 있는 권한이 없습니다"),
    NOT_VALID_TYPE("잘못된 권한입니다.");

    private final String message;
}
