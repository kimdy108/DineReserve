package com.project.dine.reserve.dto.auth.member;

import lombok.Getter;

@Getter
public class GuestLogin {
    private String guestPhone;
    private String guestPassword;
    private String guestName;
}
