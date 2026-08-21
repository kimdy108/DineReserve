package com.project.dine.reserve.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdatePassword {
    private UUID memberUUID;
    private String originPassword;
    private String newPassword;
}
