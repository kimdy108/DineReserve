package com.project.dine.reserve.dto.auth.member;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MemberRefresh {
    private String memberID;
    private UUID sessionUUID;
    private String refreshToken;
}
