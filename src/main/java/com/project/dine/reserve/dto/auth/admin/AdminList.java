package com.project.dine.reserve.dto.auth.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminList {
    private UUID adminUUID;
    private String storeName;
    private String adminID;
    private String adminName;
    private String adminEmail;
}
