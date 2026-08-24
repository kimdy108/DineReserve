package com.project.dine.reserve.dto.store.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryRegist {
    private String categoryName;
    private String categoryDescription;
}
