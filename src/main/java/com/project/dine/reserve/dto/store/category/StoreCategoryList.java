package com.project.dine.reserve.dto.store.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryList {
    private UUID categoryUUID;
    private String categoryName;
    private boolean useFlag;
    private LocalDateTime insertDate;
}
