package com.project.dine.reserve.controller.auth;

import com.project.dine.reserve.dto.auth.member.*;
import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.auth.AuthenticationGuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/guest")
@Tag(name = "비회원 인증 관리 컨트롤러", description = "비회원 인증 관리 API Controller 입니다.")
public class AuthenticationGuestController {
    private final AuthenticationGuestService authenticationGuestService;

    @Operation(summary = "guest login", description = "비회원 로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<GuestLoginResult>> guestLogin(@RequestBody GuestLogin guestLogin, HttpServletRequest request, HttpServletResponse response) {
        GuestLoginAuth guestLoginAuth = authenticationGuestService.guestLogin(guestLogin, request);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", guestLoginAuth.getAccessToken());
        response.setHeader("refreshtoken", guestLoginAuth.getRefreshToken());

        return ResponseEntity.ok(BaseResponse.success(guestLoginAuth.getLoginResult(), "비회원 로그인 되었습니다."));
    }

    @Operation(summary = "guest refresh", description = "비회원 재로그인")
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<Void>> guestRefresh(@RequestBody GuestRefresh guestRefresh, HttpServletResponse response) {
        GuestRefreshAuth guestRefreshAuth = authenticationGuestService.guestRefresh(guestRefresh);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", guestRefreshAuth.getAccessToken());
        response.setHeader("refreshtoken", guestRefreshAuth.getRefreshToken());

        return ResponseEntity.ok(BaseResponse.success("사용자 재로그인 되었습니다."));
    }
}
