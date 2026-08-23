package com.project.dine.reserve.dto.admin;

import com.project.dine.reserve.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdate {
    private UUID adminUUID;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
    private AdminRole adminRole;
}
