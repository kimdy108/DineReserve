package com.project.dine.reserve.dto.auth.member;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GuestRefresh {
    private UUID guestUUID;
    private UUID sessionUUID;
    private String refreshToken;
}
