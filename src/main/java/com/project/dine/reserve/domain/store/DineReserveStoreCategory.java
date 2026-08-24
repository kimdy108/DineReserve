package com.project.dine.reserve.domain.store;

import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.store.category.StoreCategoryRegist;
import com.project.dine.reserve.dto.store.category.StoreCategoryUpdate;
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
@Table(name = "dine_reserve_store_category", indexes = {
        @Index(name = "idx_category_uuid", columnList = "category_uuid"),
        @Index(name = "idx_category_name", columnList = "category_name")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveStoreCategory extends DineReserveBase {
    @Comment("카테고리 UUID")
    @Column(name = "category_uuid", length = 50, nullable = false, unique = true)
    private UUID categoryUUID;

    @Comment("카테고리 이름")
    @Column(name = "category_name", length = 50, nullable = false, unique = true)
    private String categoryName;

    @Comment("비고")
    @Column(name = "category_description", columnDefinition = "TEXT")
    private String categoryDescription;

    public static DineReserveStoreCategory create(StoreCategoryRegist storeCategoryRegist) {
        DineReserveStoreCategory dineReserveStoreCategory = new DineReserveStoreCategory();
        dineReserveStoreCategory.setCategoryUUID(UUID.randomUUID());
        dineReserveStoreCategory.setCategoryName(storeCategoryRegist.getCategoryName());
        dineReserveStoreCategory.setCategoryDescription(storeCategoryRegist.getCategoryDescription());

        dineReserveStoreCategory.setUseFlag(true);
        dineReserveStoreCategory.setInsertDate(LocalDateTime.now());
        dineReserveStoreCategory.setUpdateDate(LocalDateTime.now());

        return dineReserveStoreCategory;
    }

    public void update(StoreCategoryUpdate storeCategoryUpdate) {
        this.categoryName = storeCategoryUpdate.getCategoryName();
        this.categoryDescription = storeCategoryUpdate.getCategoryDescription();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateUseFlag(boolean useFlag) {
        this.setUseFlag(useFlag);
        this.setUpdateDate(LocalDateTime.now());
    }
}
