package com.project.dine.reserve.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdatePassword {
    private UUID adminUUID;
    private String oldPassword;
    private String newPassword;
}
