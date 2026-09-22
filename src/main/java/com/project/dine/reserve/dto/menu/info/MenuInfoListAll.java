package com.project.dine.reserve.dto.menu.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoListAll {
    private UUID menuInfoUUID;
    private UUID menuInfoImgUUID;
    private String menuInfoName;
    private int menuInfoPrice;
    private String menuInfoDescription;
    private boolean menuInfoOrder;
    private boolean menuInfoVisible;
}
