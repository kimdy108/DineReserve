package com.project.dine.reserve.dto.auth.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class MemberRefreshAuth {
    private String accessToken;
    private String refreshToken;

    public static MemberRefreshAuth create(String accessToken, String refreshToken) {
        MemberRefreshAuth memberRefreshAuth = new MemberRefreshAuth();
        memberRefreshAuth.setAccessToken(accessToken);
        memberRefreshAuth.setRefreshToken(refreshToken);

        return memberRefreshAuth;
    }
}
