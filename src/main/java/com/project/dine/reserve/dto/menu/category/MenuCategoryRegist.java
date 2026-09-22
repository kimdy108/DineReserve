package com.project.dine.reserve.dto.menu.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategoryRegist {
    private UUID storeUUID;
    private String menuCategoryName;
}
