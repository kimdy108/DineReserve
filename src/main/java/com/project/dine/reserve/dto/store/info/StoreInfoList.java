package com.project.dine.reserve.dto.store.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoList {
    private UUID storeUUID;
    private String categoryName;
    private String storeName;
    private String storeRegistrationNumber;
    private String storeAddress;
    private UUID storeImgUUID;
    private boolean useFlag;
    private LocalDateTime insertDate;
}
