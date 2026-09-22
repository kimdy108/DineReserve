package com.project.dine.reserve.dto.menu.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategoryListAll {
    private UUID menuCategoryUUID;
    private String menuCategoryName;
    private int menuCategorySequence;
    private boolean useFlag;
    private int menuInfoCount;
}
