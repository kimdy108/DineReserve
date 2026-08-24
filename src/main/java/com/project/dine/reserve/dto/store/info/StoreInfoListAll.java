package com.project.dine.reserve.dto.store.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoListAll {
    private UUID storeUUID;
    private String storeName;
}
