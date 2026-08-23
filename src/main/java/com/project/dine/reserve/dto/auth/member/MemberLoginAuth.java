package com.project.dine.reserve.dto.auth.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class MemberLoginAuth {
    private String accessToken;
    private String refreshToken;
    private MemberLoginResult loginResult;

    public static MemberLoginAuth create(String accessToken, String refreshToken, MemberLoginResult loginResult) {
        MemberLoginAuth memberLoginAuth = new MemberLoginAuth();
        memberLoginAuth.setAccessToken(accessToken);
        memberLoginAuth.setRefreshToken(refreshToken);
        memberLoginAuth.setLoginResult(loginResult);

        return memberLoginAuth;
    }
}
