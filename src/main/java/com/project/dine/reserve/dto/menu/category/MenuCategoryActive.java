package com.project.dine.reserve.dto.menu.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategoryActive {
    private UUID menuCategoryUUID;
    private boolean useFlag;
}
