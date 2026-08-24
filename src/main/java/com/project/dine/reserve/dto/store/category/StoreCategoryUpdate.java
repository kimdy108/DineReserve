package com.project.dine.reserve.dto.store.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryUpdate {
    private UUID categoryUUID;
    private String categoryName;
    private String categoryDescription;
}
