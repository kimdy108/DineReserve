package com.project.dine.reserve.dto.menu.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategorySequenceUpdate {
    private UUID storeUUID;
    private List<MenuCategorySequence> menuCategorySequenceList;
}
