package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.QDineReserveStoreCategory;
import com.project.dine.reserve.dto.store.category.StoreCategoryList;
import com.project.dine.reserve.dto.store.category.StoreCategoryListAll;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DineReserveStoreCategoryRepositoryImpl implements DineReserveStoreCategoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveStoreCategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveStoreCategory qDineReserveStoreCategory = QDineReserveStoreCategory.dineReserveStoreCategory;

    @Override
    public Page<StoreCategoryList> findStoreCategoryListPage(String categoryName, Long offset, int limit, Pageable pageable) {
        Long setOffset = offset * limit;

        OrderSpecifier<?> sortedColumn = qDineReserveStoreCategory.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        if (!"".equals(categoryName)) bb.and(qDineReserveStoreCategory.categoryName.containsIgnoreCase(categoryName));

        List<StoreCategoryList> storeCategoryLists = jpaQueryFactory
                .select(Projections.fields(
                        StoreCategoryList.class,
                        qDineReserveStoreCategory.categoryUUID.as("categoryUUID"),
                        qDineReserveStoreCategory.categoryName.as("categoryName"),
                        qDineReserveStoreCategory.useFlag.as("useFlag"),
                        qDineReserveStoreCategory.insertDate.as("insertDate")
                ))
                .from(qDineReserveStoreCategory)
                .where(bb)
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qDineReserveStoreCategory.seq.count())
                .from(qDineReserveStoreCategory)
                .where(bb);

        return PageableExecutionUtils.getPage(storeCategoryLists, pageable, countQuery::fetchOne);
    }

    @Override
    public List<StoreCategoryListAll> findStoreCategoryListAll() {
        OrderSpecifier<?> sortedColumn = qDineReserveStoreCategory.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveStoreCategory.useFlag.eq(true));

        return jpaQueryFactory
                .select(Projections.fields(
                        StoreCategoryListAll.class,
                        qDineReserveStoreCategory.categoryUUID.as("categoryUUID"),
                        qDineReserveStoreCategory.categoryName.as("categoryName")
                ))
                .from(qDineReserveStoreCategory)
                .where(bb)
                .orderBy(sortedColumn)
                .fetch();
    }
}
