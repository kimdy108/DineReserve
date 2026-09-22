package com.project.dine.reserve.domain.menu;

import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.menu.category.MenuCategoryRegist;
import com.project.dine.reserve.dto.menu.category.MenuCategoryUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_menu_category", indexes = {
        @Index(name = "idx_menu_category_uuid", columnList = "menu_category_uuid"),
        @Index(name = "idx_store_uuid", columnList = "store_uuid"),
        @Index(name = "idx_menu_category_name", columnList = "menu_category_name")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveMenuCategory extends DineReserveBase {
    @Comment("메뉴 카테고리 UUID")
    @Column(name = "menu_category_uuid", length = 50, nullable = false, unique = true)
    private UUID menuCategoryUUID;

    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false)
    private UUID storeUUID;

    @Comment("메뉴 카테고리 이름")
    @Column(name = "menu_category_name", length = 20, nullable = false)
    private String menuCategoryName;

    @Comment("메뉴 카테고리 순서")
    @Column(name = "menu_category_order", length = 10, nullable = false)
    private int menuCategoryOrder;

    public static DineReserveMenuCategory create(MenuCategoryRegist menuCategoryRegist, int maxOrder, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveMenuCategory dineReserveMenuCategory = new DineReserveMenuCategory();
        dineReserveMenuCategory.setMenuCategoryUUID(UUID.randomUUID());
        dineReserveMenuCategory.setStoreSeq(dineReserveStoreInfo.getSeq());
        dineReserveMenuCategory.setStoreUUID(dineReserveStoreInfo.getStoreUUID());
        dineReserveMenuCategory.setMenuCategoryName(menuCategoryRegist.getMenuCategoryName());
        dineReserveMenuCategory.setMenuCategoryOrder(maxOrder + 1);

        dineReserveMenuCategory.setUseFlag(true);
        dineReserveMenuCategory.setInsertDate(LocalDateTime.now());
        dineReserveMenuCategory.setUpdateDate(LocalDateTime.now());

        return dineReserveMenuCategory;
    }

    public void update(MenuCategoryUpdate menuCategoryUpdate) {
        this.menuCategoryName = menuCategoryUpdate.getMenuCategoryName();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateOrder(int order) {
        this.menuCategoryOrder = order;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateUseFlag(boolean useFlag) {
        this.setUseFlag(useFlag);
        this.setUpdateDate(LocalDateTime.now());
    }
}
