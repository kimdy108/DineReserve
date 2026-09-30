package com.project.dine.reserve.dto.auth.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PROTECTED)
public class GuestLoginResult {
    private UUID guestUUID;
    private UUID sessionUUID;

    public static GuestLoginResult create(UUID guestUUID, UUID sessionUUID) {
        GuestLoginResult guestLoginResult = new GuestLoginResult();
        guestLoginResult.setGuestUUID(guestUUID);
        guestLoginResult.setSessionUUID(sessionUUID);

        return guestLoginResult;
    }
}
