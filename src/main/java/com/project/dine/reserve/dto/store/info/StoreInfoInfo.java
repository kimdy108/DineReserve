package com.project.dine.reserve.dto.store.info;

import com.project.dine.reserve.domain.store.DineReserveStoreCategory;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoInfo {
    private UUID storeUUID;
    private String categoryName;
    private String storeName;
    private String storeRegistrationNumber;
    private String storeOwnerName;
    private String storeNumber;
    private String storeAddress;
    private String storeDescription;
    private UUID storeImgUUID;
    private UUID storeMapUUID;

    public static StoreInfoInfo create(DineReserveStoreInfo dineReserveStoreInfo, DineReserveStoreCategory dineReserveStoreCategory) {
        StoreInfoInfo storeInfoInfo = new StoreInfoInfo();
        storeInfoInfo.setStoreUUID(dineReserveStoreInfo.getStoreUUID());
        storeInfoInfo.setCategoryName(dineReserveStoreCategory.getCategoryName());
        storeInfoInfo.setStoreName(dineReserveStoreInfo.getStoreName());
        storeInfoInfo.setStoreRegistrationNumber(dineReserveStoreInfo.getStoreRegistrationNumber());
        storeInfoInfo.setStoreOwnerName(dineReserveStoreInfo.getStoreOwnerName());
        storeInfoInfo.setStoreNumber(dineReserveStoreInfo.getStoreNumber());
        storeInfoInfo.setStoreAddress(dineReserveStoreInfo.getStoreAddress());
        storeInfoInfo.setStoreDescription(dineReserveStoreInfo.getStoreDescription());
        storeInfoInfo.setStoreImgUUID(dineReserveStoreInfo.getStoreImgUUID());
        storeInfoInfo.setStoreMapUUID(dineReserveStoreInfo.getStoreMapUUID());

        return storeInfoInfo;
    }
}
