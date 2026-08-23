package com.project.dine.reserve.dto.auth.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class AdminLoginAuth {
    private String accessToken;
    private String refreshToken;
    private AdminLoginResult loginResult;

    public static AdminLoginAuth create(String accessToken, String refreshToken, AdminLoginResult loginResult) {
        AdminLoginAuth adminLoginAuth = new AdminLoginAuth();
        adminLoginAuth.setAccessToken(accessToken);
        adminLoginAuth.setRefreshToken(refreshToken);
        adminLoginAuth.setLoginResult(loginResult);

        return adminLoginAuth;
    }
}
