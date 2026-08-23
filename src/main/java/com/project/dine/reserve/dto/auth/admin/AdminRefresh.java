package com.project.dine.reserve.dto.auth.admin;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AdminRefresh {
    private String adminID;
    private UUID sessionUUID;
    private String refreshToken;
}
