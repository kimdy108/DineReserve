package com.project.dine.reserve.controller.auth;

import com.project.dine.reserve.dto.auth.member.*;
import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.auth.AuthenticationMemberService;
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
@RequestMapping("/api/auth/member")
@Tag(name = "사용자 인증 관리 컨트롤러", description = "사용자 인증 관리 API Controller 입니다.")
public class AuthenticationMemberController {
    private final AuthenticationMemberService authenticationMemberService;

    @Operation(summary = "member login", description = "사용자 로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<MemberLoginResult>> memberLogin(@RequestBody MemberLogin memberLogin, HttpServletRequest request, HttpServletResponse response) {
        MemberLoginAuth memberLoginAuth = authenticationMemberService.memberLogin(memberLogin, request);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", memberLoginAuth.getAccessToken());
        response.setHeader("refreshtoken", memberLoginAuth.getRefreshToken());

        return ResponseEntity.ok(BaseResponse.success(memberLoginAuth.getLoginResult(), "사용자 로그인 되었습니다."));
    }

    @Operation(summary = "member refresh", description = "사용자 재로그인")
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<Void>> memberRefresh(@RequestBody MemberRefresh memberRefresh, HttpServletResponse response) {
        MemberRefreshAuth memberRefreshAuth = authenticationMemberService.memberRefresh(memberRefresh);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", memberRefreshAuth.getAccessToken());
        response.setHeader("refreshtoken", memberRefreshAuth.getRefreshToken());

        return ResponseEntity.ok(BaseResponse.success("사용자 재로그인 되었습니다."));
    }
}
