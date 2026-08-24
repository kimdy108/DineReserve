package com.project.dine.reserve.dto.store.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoUpdate {
    private UUID storeUUID;
    private String storeName;
    private String storeOwnerName;
    private String storeNumber;
    private String storeAddress;
    private String storeDescription;

    private MultipartFile storeImg;
    private MultipartFile storeMap;
}
