package com.project.dine.reserve.dto.auth.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PROTECTED)
public class AdminLoginResult {
    private UUID adminUUID;
    private UUID sessionUUID;

    public static AdminLoginResult create(UUID adminUUID, UUID sessionUUID) {
        AdminLoginResult adminLoginResult = new AdminLoginResult();
        adminLoginResult.setAdminUUID(adminUUID);
        adminLoginResult.setSessionUUID(sessionUUID);

        return adminLoginResult;
    }
}
