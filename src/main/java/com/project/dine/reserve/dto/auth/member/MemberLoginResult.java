package com.project.dine.reserve.dto.auth.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PROTECTED)
public class MemberLoginResult {
    private UUID memberUUID;
    private UUID sessionUUID;

    public static MemberLoginResult create(UUID memberUUID, UUID sessionUUID) {
        MemberLoginResult memberLoginResult = new MemberLoginResult();
        memberLoginResult.setMemberUUID(memberUUID);
        memberLoginResult.setSessionUUID(sessionUUID);

        return memberLoginResult;
    }
}
