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
}
