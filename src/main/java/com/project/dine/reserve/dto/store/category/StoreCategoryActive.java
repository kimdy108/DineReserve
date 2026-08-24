package com.project.dine.reserve.dto.store.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryActive {
    private UUID categoryUUID;
    private boolean useFlag;
}
