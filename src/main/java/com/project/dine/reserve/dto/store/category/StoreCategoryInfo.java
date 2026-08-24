package com.project.dine.reserve.dto.store.category;

import com.project.dine.reserve.domain.store.DineReserveStoreCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryInfo {
    private UUID categoryUUID;
    private String categoryName;
    private String categoryDescription;

    public static StoreCategoryInfo create(DineReserveStoreCategory dineReserveStoreCategory) {
        StoreCategoryInfo storeCategoryInfo = new StoreCategoryInfo();
        storeCategoryInfo.setCategoryUUID(dineReserveStoreCategory.getCategoryUUID());
        storeCategoryInfo.setCategoryName(dineReserveStoreCategory.getCategoryName());
        storeCategoryInfo.setCategoryDescription(dineReserveStoreCategory.getCategoryDescription());

        return storeCategoryInfo;
    }
}
