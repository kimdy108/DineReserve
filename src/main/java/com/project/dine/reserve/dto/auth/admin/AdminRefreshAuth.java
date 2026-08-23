package com.project.dine.reserve.dto.auth.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class AdminRefreshAuth {
    private String accessToken;
    private String refreshToken;

    public static AdminRefreshAuth create(String accessToken, String refreshToken) {
        AdminRefreshAuth adminRefreshAuth = new AdminRefreshAuth();
        adminRefreshAuth.setAccessToken(accessToken);
        adminRefreshAuth.setRefreshToken(refreshToken);

        return adminRefreshAuth;
    }
}
