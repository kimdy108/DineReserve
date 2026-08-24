package com.project.dine.reserve.domain.store;

import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.domain.system.DineReserveFile;
import com.project.dine.reserve.dto.store.info.StoreInfoRegist;
import com.project.dine.reserve.dto.store.info.StoreInfoUpdate;
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
@Table(name = "dine_reserve_store_info", indexes = {
        @Index(name = "idx_store_uuid", columnList = "store_uuid"),
        @Index(name = "idx_category_seq", columnList = "category_seq"),
        @Index(name = "idx_category_uuid", columnList = "category_uuid"),
        @Index(name = "idx_store_img_seq", columnList = "store_img_seq"),
        @Index(name = "idx_store_img_uuid", columnList = "store_img_uuid"),
        @Index(name = "idx_store_map_seq", columnList = "store_map_seq"),
        @Index(name = "idx_store_map_uuid", columnList = "store_map_uuid"),
        @Index(name = "idx_store_name", columnList = "store_name"),
        @Index(name = "idx_store_registration_number", columnList = "store_registration_number"),
        @Index(name = "idx_store_number", columnList = "store_number")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveStoreInfo extends DineReserveBase {
    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false, unique = true)
    private UUID storeUUID;

    @Comment("카테고리 SEQ")
    @Column(name = "category_seq", length = 20, nullable = false)
    private Long categorySeq;

    @Comment("카테고리 UUID")
    @Column(name = "category_uuid", length = 50, nullable = false)
    private UUID categoryUUID;

    @Comment("매장 대표 이미지 SEQ")
    @Column(name = "store_img_seq", length = 20, nullable = false)
    private Long storeImgSeq;

    @Comment("매장 대표 이미지 UUID")
    @Column(name = "store_img_uuid", length = 50, nullable = false)
    private UUID storeImgUUID;

    @Comment("매장 대표 약도 SEQ")
    @Column(name = "store_map_seq", length = 20, nullable = false)
    private Long storeMapSeq;

    @Comment("매장 대표 약도 UUID")
    @Column(name = "store_map_uuid", length = 50, nullable = false)
    private UUID storeMapUUID;

    @Comment("매장 이름")
    @Column(name = "store_name", length = 100, nullable = false)
    private String storeName;

    @Comment("매장 사업자번호")
    @Column(name = "store_registration_number", length = 20, nullable = false)
    private String storeRegistrationNumber;

    @Comment("매장 대표자 이름")
    @Column(name = "store_owner_name", length = 10, nullable = false)
    private String storeOwnerName;

    @Comment("매장 연락처")
    @Column(name = "store_number", length = 20, nullable = false)
    private String storeNumber;

    @Comment("매장 주소")
    @Column(name = "store_address", length = 200, nullable = false)
    private String storeAddress;

    @Comment("매장 비고")
    @Column(name = "store_description", columnDefinition = "TEXT")
    private String storeDescription;

    public static DineReserveStoreInfo create(StoreInfoRegist storeInfoRegist, DineReserveStoreCategory dineReserveStoreCategory, DineReserveFile storeImg, DineReserveFile storeMap) {
        DineReserveStoreInfo dineReserveStoreInfo = new DineReserveStoreInfo();
        dineReserveStoreInfo.setStoreUUID(UUID.randomUUID());
        dineReserveStoreInfo.setCategorySeq(dineReserveStoreCategory.getSeq());
        dineReserveStoreInfo.setCategoryUUID(dineReserveStoreCategory.getCategoryUUID());
        dineReserveStoreInfo.setStoreImgSeq(storeImg == null ? EMPTY_SEQ : storeImg.getSeq());
        dineReserveStoreInfo.setStoreImgUUID(storeImg == null ? EMPTY_UUID : storeImg.getFileUUID());
        dineReserveStoreInfo.setStoreMapSeq(storeMap == null ? EMPTY_SEQ : storeMap.getSeq());
        dineReserveStoreInfo.setStoreMapUUID(storeMap == null ? EMPTY_UUID : storeMap.getFileUUID());
        dineReserveStoreInfo.setStoreName(storeInfoRegist.getStoreName());
        dineReserveStoreInfo.setStoreRegistrationNumber(storeInfoRegist.getStoreRegistrationNumber());
        dineReserveStoreInfo.setStoreOwnerName(storeInfoRegist.getStoreOwnerName());
        dineReserveStoreInfo.setStoreNumber(storeInfoRegist.getStoreNumber());
        dineReserveStoreInfo.setStoreAddress(storeInfoRegist.getStoreAddress());
        dineReserveStoreInfo.setStoreDescription(storeInfoRegist.getStoreDescription());

        dineReserveStoreInfo.setUseFlag(true);
        dineReserveStoreInfo.setInsertDate(LocalDateTime.now());
        dineReserveStoreInfo.setUpdateDate(LocalDateTime.now());

        return dineReserveStoreInfo;
    }

    public void update(StoreInfoUpdate storeInfoUpdate, DineReserveFile storeImg, DineReserveFile storeMap) {
        this.storeImgSeq = storeImg == null ? EMPTY_SEQ : storeImg.getSeq();
        this.storeImgUUID = storeImg == null ? EMPTY_UUID : storeImg.getFileUUID();
        this.storeMapSeq = storeMap == null ? EMPTY_SEQ : storeMap.getSeq();
        this.storeMapUUID = storeMap == null ? EMPTY_UUID : storeMap.getFileUUID();
        this.storeName = storeInfoUpdate.getStoreName();
        this.storeOwnerName = storeInfoUpdate.getStoreOwnerName();
        this.storeNumber = storeInfoUpdate.getStoreNumber();
        this.storeAddress = storeInfoUpdate.getStoreAddress();
        this.storeDescription = storeInfoUpdate.getStoreDescription();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateUseFlag(boolean useFlag) {
        this.setUseFlag(useFlag);
        this.setUpdateDate(LocalDateTime.now());
    }
}
