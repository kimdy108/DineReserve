package com.project.dine.reserve.controller.common;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.component.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final LoggerService loggerService;

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<BaseResponse<String>> handleRuntimeException(RuntimeException e) {
        loggerService.writeLogger("error", "Runtime Error : " + e.getMessage());
        e.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(e.getMessage()));
    }
}
