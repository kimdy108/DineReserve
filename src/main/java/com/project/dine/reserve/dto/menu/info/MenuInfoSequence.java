package com.project.dine.reserve.dto.menu.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoSequence {
    private UUID menuInfoUUID;
    private int menuInfoSequence;
}
