package com.project.dine.reserve.dto.auth.admin;

import com.project.dine.reserve.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegist {
    private UUID storeUUID;
    private String adminID;
    private String adminPassword;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
    private AdminRole adminRole;
    private String adminDescription;
}
