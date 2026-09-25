package com.project.dine.reserve.domain.store;

import com.project.dine.reserve.domain.common.DineReserveBase;
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
@Table(name = "dine_reserve_store_category_info", indexes = {
        @Index(name = "idx_category_seq", columnList = "category_seq"),
        @Index(name = "idx_category_uuid", columnList = "category_uuid"),
        @Index(name = "idx_store_seq", columnList = "store_seq"),
        @Index(name = "idx_store_uuid", columnList = "store_uuid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveStoreCategoryInfo extends DineReserveBase {
    @Comment("카테고리 SEQ")
    @Column(name = "category_seq", length = 20, nullable = false)
    private Long categorySeq;

    @Comment("카테고리 UUID")
    @Column(name = "category_uuid", length = 50, nullable = false)
    private UUID categoryUUID;

    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false)
    private UUID storeUUID;

    public static DineReserveStoreCategoryInfo create(DineReserveStoreCategory dineReserveStoreCategory, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveStoreCategoryInfo dineReserveStoreCategoryInfo = new DineReserveStoreCategoryInfo();
        dineReserveStoreCategoryInfo.setCategorySeq(dineReserveStoreCategory.getSeq());
        dineReserveStoreCategoryInfo.setCategoryUUID(dineReserveStoreCategory.getCategoryUUID());
        dineReserveStoreCategoryInfo.setStoreSeq(dineReserveStoreInfo.getSeq());
        dineReserveStoreCategoryInfo.setStoreUUID(dineReserveStoreInfo.getStoreUUID());

        dineReserveStoreCategory.setUseFlag(true);
        dineReserveStoreCategory.setInsertDate(LocalDateTime.now());
        dineReserveStoreCategory.setUpdateDate(LocalDateTime.now());

        return dineReserveStoreCategoryInfo;
    }
}
