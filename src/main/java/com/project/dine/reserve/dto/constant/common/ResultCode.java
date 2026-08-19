package com.project.dine.reserve.dto.constant.common;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum ResultCode {
    SUCCESS,
    ERROR,
    BAD_REQUEST,
    UNAUTHORIZED,
    TOKEN_EXPIRED
}
