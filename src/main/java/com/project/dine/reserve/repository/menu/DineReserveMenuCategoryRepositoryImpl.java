package com.project.dine.reserve.repository.menu;

import com.project.dine.reserve.domain.menu.QDineReserveMenuCategory;
import com.project.dine.reserve.dto.menu.category.MenuCategoryListAll;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DineReserveMenuCategoryRepositoryImpl implements DineReserveMenuCategoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveMenuCategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveMenuCategory qDineReserveMenuCategory = QDineReserveMenuCategory.dineReserveMenuCategory;

    @Override
    public List<MenuCategoryListAll> findMenuCategoryListAll(UUID storeUUID, boolean isTotal) {
        OrderSpecifier<?> sortedColumn = qDineReserveMenuCategory.menuCategorySequence.asc();

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveMenuCategory.storeUUID.eq(storeUUID));
        if (!isTotal) bb.and(qDineReserveMenuCategory.useFlag.eq(true));

        return jpaQueryFactory
                .select(Projections.fields(
                        MenuCategoryListAll.class,
                        qDineReserveMenuCategory.menuCategoryUUID.as("menuCategoryUUID"),
                        qDineReserveMenuCategory.menuCategoryName.as("menuCategoryName"),
                        qDineReserveMenuCategory.menuCategorySequence.as("menuCategorySequence"),
                        qDineReserveMenuCategory.useFlag.as("useFlag")
                ))
                .from(qDineReserveMenuCategory)
                .where(bb)
                .orderBy(sortedColumn)
                .fetch();
    }
}
