package com.project.dine.reserve.dto.admin;

import com.project.dine.reserve.domain.admin.DineReserveAdmin;
import com.project.dine.reserve.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfo {
    private UUID adminUUID;
    private String adminID;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
    private AdminRole adminRole;
    private String adminDescription;

    public static AdminInfo create(DineReserveAdmin dineReserveAdmin) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminUUID(dineReserveAdmin.getAdminUUID());
        adminInfo.setAdminID(dineReserveAdmin.getAdminID());
        adminInfo.setAdminName(dineReserveAdmin.getAdminName());
        adminInfo.setAdminPhone(dineReserveAdmin.getAdminPhone());
        adminInfo.setAdminEmail(dineReserveAdmin.getAdminEmail());
        adminInfo.setAdminRole(dineReserveAdmin.getAdminRole());
        adminInfo.setAdminDescription(dineReserveAdmin.getAdminDescription());

        return adminInfo;
    }
}
