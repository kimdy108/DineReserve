package com.project.dine.reserve.dto.common;

import com.project.dine.reserve.dto.constant.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private String resultCode;
    private T data;
    private String message;

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .resultCode(ResultCode.SUCCESS.name())
                .data(data)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .resultCode(ResultCode.SUCCESS.name())
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> success(String message) {
        return BaseResponse.<T>builder()
                .resultCode(ResultCode.SUCCESS.name())
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> error(String message) {
        return BaseResponse.<T>builder()
                .resultCode(ResultCode.BAD_REQUEST.name())
                .message(message)
                .build();
    }

////////// security filter exception response //////////

    public static <T> BaseResponse<T> unAuthorized(String message) {
        return BaseResponse.<T>builder()
                .resultCode(ResultCode.UNAUTHORIZED.name())
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> tokenExpired(String message) {
        return BaseResponse.<T>builder()
                .resultCode(ResultCode.TOKEN_EXPIRED.name())
                .message(message)
                .build();
    }
}
