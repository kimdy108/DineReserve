package com.project.dine.reserve.dto.auth.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class GuestLoginAuth {
    private String accessToken;
    private String refreshToken;
    private GuestLoginResult loginResult;

    public static GuestLoginAuth create(String accessToken, String refreshToken, GuestLoginResult loginResult) {
        GuestLoginAuth guestLoginAuth = new GuestLoginAuth();
        guestLoginAuth.setAccessToken(accessToken);
        guestLoginAuth.setRefreshToken(refreshToken);
        guestLoginAuth.setLoginResult(loginResult);

        return guestLoginAuth;
    }
}
