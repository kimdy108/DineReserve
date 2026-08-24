package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.QDineReserveStoreCategory;
import com.project.dine.reserve.domain.store.QDineReserveStoreInfo;
import com.project.dine.reserve.dto.store.info.StoreInfoList;
import com.project.dine.reserve.dto.store.info.StoreInfoListAll;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DineReserveStoreInfoRepositoryImpl implements DineReserveStoreInfoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveStoreInfoRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveStoreCategory qDineReserveStoreCategory = QDineReserveStoreCategory.dineReserveStoreCategory;
    QDineReserveStoreInfo qDineReserveStoreInfo = QDineReserveStoreInfo.dineReserveStoreInfo;

    @Override
    public Page<StoreInfoList> findStoreInfoListPage(String searchType, String searchValue, String categoryUUID, String storeStatus, Long offset, int limit, Pageable pageable) {
        Long setOffset = offset * limit;

        OrderSpecifier<?> sortedColumn = qDineReserveStoreInfo.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        if (!"".equals(categoryUUID)) bb.and(qDineReserveStoreInfo.categoryUUID.eq(UUID.fromString(categoryUUID)));

        switch (storeStatus) {
            case "ACTIVE" -> bb.and(qDineReserveStoreInfo.useFlag.eq(true));
            case "INACTIVE" -> bb.and(qDineReserveStoreInfo.useFlag.eq(false));
        }

        List<StoreInfoList> storeInfoLists = jpaQueryFactory
                .select(Projections.fields(
                        StoreInfoList.class,
                        qDineReserveStoreInfo.storeUUID.as("storeUUID"),
                        qDineReserveStoreCategory.categoryName.as("categoryName"),
                        qDineReserveStoreInfo.storeName.as("storeName"),
                        qDineReserveStoreInfo.storeRegistrationNumber.as("storeRegistrationNumber"),
                        qDineReserveStoreInfo.storeAddress.as("storeAddress"),
                        qDineReserveStoreInfo.storeImgUUID.as("storeImgUUID"),
                        qDineReserveStoreInfo.useFlag.as("useFlag"),
                        qDineReserveStoreInfo.insertDate.as("insertDate")
                ))
                .from(qDineReserveStoreInfo)
                .leftJoin(qDineReserveStoreCategory).on(qDineReserveStoreInfo.categorySeq.eq(qDineReserveStoreCategory.seq))
                .where(bb, eqStoreName(searchType, searchValue), eqStoreNumber(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qDineReserveStoreInfo.seq.count())
                .from(qDineReserveStoreInfo)
                .leftJoin(qDineReserveStoreCategory).on(qDineReserveStoreInfo.categorySeq.eq(qDineReserveStoreCategory.seq))
                .where(bb, eqStoreName(searchType, searchValue), eqStoreNumber(searchType, searchValue));

        return PageableExecutionUtils.getPage(storeInfoLists, pageable, countQuery::fetchOne);
    }

    @Override
    public List<StoreInfoListAll> findStoreInfoListAll() {
        OrderSpecifier<?> sortedColumn = qDineReserveStoreInfo.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveStoreInfo.useFlag.eq(true));

        return jpaQueryFactory
                .select(Projections.fields(
                        StoreInfoListAll.class,
                        qDineReserveStoreInfo.storeUUID.as("storeUUID"),
                        qDineReserveStoreInfo.storeName.as("storeName")
                ))
                .from(qDineReserveStoreInfo)
                .where(bb)
                .orderBy(sortedColumn)
                .fetch();
    }

    private BooleanExpression eqStoreName(String searchType, String searchValue) {
        if (!"storeName".equals(searchType) || "".equals(searchValue)) return null;
        return qDineReserveStoreInfo.storeName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqStoreNumber(String searchType, String searchValue) {
        if (!"storeNumber".equals(searchType) || "".equals(searchValue)) return null;
        return qDineReserveStoreInfo.storeNumber.containsIgnoreCase(searchValue);
    }
}
