package com.project.dine.reserve.dto.menu.info;

import com.project.dine.reserve.domain.menu.DineReserveMenuCategory;
import com.project.dine.reserve.domain.menu.DineReserveMenuInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoInfo {
    private UUID menuInfoUUID;
    private String menuCategoryName;
    private UUID menuInfoImgUUID;
    private String menuInfoName;
    private int menuInfoPrice;
    private String menuInfoDescription;
    private boolean menuInfoOrder;
    private boolean menuInfoVisible;

    public static MenuInfoInfo create(DineReserveMenuInfo dineReserveMenuInfo, DineReserveMenuCategory dineReserveMenuCategory) {
        MenuInfoInfo menuInfoInfo = new MenuInfoInfo();
        menuInfoInfo.setMenuInfoUUID(dineReserveMenuInfo.getMenuInfoUUID());
        menuInfoInfo.setMenuCategoryName(dineReserveMenuCategory.getMenuCategoryName());
        menuInfoInfo.setMenuInfoImgUUID(dineReserveMenuInfo.getMenuInfoImgUUID());
        menuInfoInfo.setMenuInfoName(dineReserveMenuInfo.getMenuInfoName());
        menuInfoInfo.setMenuInfoPrice(dineReserveMenuInfo.getMenuInfoPrice());
        menuInfoInfo.setMenuInfoDescription(dineReserveMenuInfo.getMenuInfoDescription());
        menuInfoInfo.setMenuInfoOrder(dineReserveMenuInfo.isMenuInfoOrder());
        menuInfoInfo.setMenuInfoVisible(dineReserveMenuInfo.isMenuInfoVisible());

        return menuInfoInfo;
    }
}
