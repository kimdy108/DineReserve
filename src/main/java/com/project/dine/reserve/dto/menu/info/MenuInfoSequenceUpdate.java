package com.project.dine.reserve.dto.menu.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoSequenceUpdate {
    private UUID menuCategoryUUID;
    private List<MenuInfoSequence> menuInfoSequenceList;
}
