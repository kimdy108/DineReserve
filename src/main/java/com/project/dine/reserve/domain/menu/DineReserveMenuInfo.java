package com.project.dine.reserve.domain.menu;

import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.domain.system.DineReserveFile;
import com.project.dine.reserve.dto.menu.info.MenuInfoRegist;
import com.project.dine.reserve.dto.menu.info.MenuInfoUpdate;
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

import static com.project.dine.reserve.util.Common.EMPTY_SEQ;
import static com.project.dine.reserve.util.Common.EMPTY_UUID;

@Entity
@Table(name = "dine_reserve_menu_info", indexes = {
        @Index(name = "idx_menu_info_uuid", columnList = "menu_info_uuid"),
        @Index(name = "idx_menu_category_seq", columnList = "menu_category_seq"),
        @Index(name = "idx_menu_category_uuid", columnList = "menu_category_uuid"),
        @Index(name = "idx_menu_info_img_seq", columnList = "menu_info_img_seq"),
        @Index(name = "idx_menu_info_img_uuid", columnList = "menu_info_img_uuid"),
        @Index(name = "idx_menu_info_name", columnList = "menu_info_name"),
        @Index(name = "idx_menu_info_sequence", columnList = "menu_info_sequence")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveMenuInfo extends DineReserveBase {
    @Comment("메뉴 UUID")
    @Column(name = "menu_info_uuid", length = 50, nullable = false, unique = true)
    private UUID menuInfoUUID;

    @Comment("메뉴 카테고리 SEQ")
    @Column(name = "menu_category_seq", length = 20, nullable = false)
    private Long menuCategorySeq;

    @Comment("메뉴 카테고리 UUID")
    @Column(name = "menu_category_uuid", length = 50, nullable = false)
    private UUID menuCategoryUUID;

    @Comment("메뉴 이미지 SEQ")
    @Column(name = "menu_info_img_seq", length = 20, nullable = false)
    private Long menuInfoImgSeq;

    @Comment("매뉴 이미지 UUID")
    @Column(name = "menu_info_img_uuid", length = 50, nullable = false)
    private UUID menuInfoImgUUID;

    @Comment("메뉴 명")
    @Column(name = "menu_info_name", length = 50, nullable = false)
    private String menuInfoName;

    @Comment("메뉴 가격")
    @Column(name = "menu_info_price", length = 20, nullable = false)
    private int menuInfoPrice;

    @Comment("메뉴 설명")
    @Column(name = "menu_info_description", columnDefinition = "TEXT")
    private String menuInfoDescription;

    @Comment("메뉴 주문 여부")
    @Column(name = "menu_info_order", columnDefinition = "bit(1) default true")
    private boolean menuInfoOrder;

    @Comment("메뉴 노출 여부")
    @Column(name = "menu_info_visible", columnDefinition = "bit(1) default true")
    private boolean menuInfoVisible;

    @Comment("메뉴 순서")
    @Column(name = "menu_info_sequence", length = 10, nullable = false)
    private int menuInfoSequence;

    public static DineReserveMenuInfo create(MenuInfoRegist menuInfoRegist, int maxSequence, DineReserveMenuCategory dineReserveMenuCategory, DineReserveFile menuInfoImg) {
        DineReserveMenuInfo dineReserveMenuInfo = new DineReserveMenuInfo();
        dineReserveMenuInfo.setMenuInfoUUID(UUID.randomUUID());
        dineReserveMenuInfo.setMenuCategorySeq(dineReserveMenuCategory.getSeq());
        dineReserveMenuInfo.setMenuCategoryUUID(dineReserveMenuCategory.getMenuCategoryUUID());
        dineReserveMenuInfo.setMenuInfoImgSeq(menuInfoImg == null ? EMPTY_SEQ : menuInfoImg.getSeq());
        dineReserveMenuInfo.setMenuInfoImgUUID(menuInfoImg == null ? EMPTY_UUID : menuInfoImg.getFileUUID());
        dineReserveMenuInfo.setMenuInfoName(menuInfoRegist.getMenuInfoName());
        dineReserveMenuInfo.setMenuInfoPrice(menuInfoRegist.getMenuInfoPrice());
        dineReserveMenuInfo.setMenuInfoDescription(menuInfoRegist.getMenuInfoDescription());
        dineReserveMenuInfo.setMenuInfoOrder(menuInfoRegist.isMenuInfoOrder());
        dineReserveMenuInfo.setMenuInfoVisible(menuInfoRegist.isMenuInfoVisible());
        dineReserveMenuInfo.setMenuInfoSequence(maxSequence + 1);

        dineReserveMenuInfo.setUseFlag(true);
        dineReserveMenuInfo.setInsertDate(LocalDateTime.now());
        dineReserveMenuInfo.setUpdateDate(LocalDateTime.now());

        return dineReserveMenuInfo;
    }

    public void update(MenuInfoUpdate menuInfoUpdate, DineReserveFile menuInfoImg) {
        this.menuInfoImgSeq = menuInfoImg == null ? EMPTY_SEQ : menuInfoImg.getSeq();
        this.menuInfoImgUUID = menuInfoImg == null ? EMPTY_UUID : menuInfoImg.getFileUUID();
        this.menuInfoName = menuInfoUpdate.getMenuInfoName();
        this.menuInfoPrice = menuInfoUpdate.getMenuInfoPrice();
        this.menuInfoDescription = menuInfoUpdate.getMenuInfoDescription();
        this.menuInfoOrder = menuInfoUpdate.isMenuInfoOrder();
        this.menuInfoVisible = menuInfoUpdate.isMenuInfoVisible();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateSequence(int menuInfoSequence) {
        this.menuInfoSequence = menuInfoSequence;

        this.setUpdateDate(LocalDateTime.now());
    }
}
