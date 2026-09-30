package com.project.dine.reserve.dto.auth.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class GuestRefreshAuth {
    private String accessToken;
    private String refreshToken;

    public static GuestRefreshAuth create(String accessToken, String refreshToken) {
        GuestRefreshAuth guestRefreshAuth = new GuestRefreshAuth();
        guestRefreshAuth.setAccessToken(accessToken);
        guestRefreshAuth.setRefreshToken(refreshToken);

        return guestRefreshAuth;
    }
}
