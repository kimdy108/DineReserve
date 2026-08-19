package com.project.dine.reserve.config.exception;

import lombok.Getter;

@Getter
public class DineReserveException extends RuntimeException {
    public DineReserveException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
