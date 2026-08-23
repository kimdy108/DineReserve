package com.project.dine.reserve.controller.auth;

import com.project.dine.reserve.dto.auth.admin.*;
import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.auth.AuthenticationAdminService;
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
@RequestMapping("/api/auth/admin")
@Tag(name = "관리자 인증 관리 컨트롤러", description = "관리자 인증 관리 API Controller 입니다.")
public class AuthenticationAdminController {
    private final AuthenticationAdminService authenticationAdminService;

    @Operation(summary = "admin login", description = "관리자 로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AdminLoginResult>> adminLogin(@RequestBody AdminLogin adminLogin, HttpServletRequest request, HttpServletResponse response) {
        AdminLoginAuth adminLoginAuth = authenticationAdminService.adminLogin(adminLogin, request);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", adminLoginAuth.getAccessToken());
        response.setHeader("refreshtoken", adminLoginAuth.getRefreshToken());

        return ResponseEntity.ok(BaseResponse.success(adminLoginAuth.getLoginResult(), "관리자 로그인 되었습니다."));
    }

    @Operation(summary = "admin refresh", description = "관리자 재로그인")
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<Void>> adminRefresh(@RequestBody AdminRefresh adminRefresh, HttpServletResponse response) {
        AdminRefreshAuth adminRefreshAuth = authenticationAdminService.adminRefresh(adminRefresh);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", adminRefreshAuth.getAccessToken());
        response.setHeader("refreshtoken", adminRefreshAuth.getRefreshToken());

        return ResponseEntity.ok(BaseResponse.success("관리자 재로그인 되었습니다."));
    }
}
