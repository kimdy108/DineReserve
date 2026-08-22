package com.project.dine.reserve.repository.admin;

import com.project.dine.reserve.domain.admin.QDineReserveAdmin;
import com.project.dine.reserve.domain.store.QDineReserveStoreInfo;
import com.project.dine.reserve.dto.auth.admin.AdminList;
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

@Repository
public class DineReserveAdminRepositoryImpl implements DineReserveAdminRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveAdminRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveAdmin qDineReserveAdmin = QDineReserveAdmin.dineReserveAdmin;
    QDineReserveStoreInfo qDineReserveStoreInfo = QDineReserveStoreInfo.dineReserveStoreInfo;

    @Override
    public Page<AdminList> findAdminListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable) {
        Long setOffset = offset * limit;

        OrderSpecifier<?> sortedColumn = qDineReserveAdmin.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveAdmin.useFlag.eq(true));

        List<AdminList> adminLists = jpaQueryFactory
                .select(Projections.fields(
                        AdminList.class,
                        qDineReserveAdmin.adminUUID.as("adminUUID"),
                        qDineReserveStoreInfo.storeName.as("storeName"),
                        qDineReserveAdmin.adminID.as("adminID"),
                        qDineReserveAdmin.adminName.as("adminName"),
                        qDineReserveAdmin.adminEmail.as("adminEmail")
                ))
                .from(qDineReserveAdmin)
                .leftJoin(qDineReserveStoreInfo).on(qDineReserveAdmin.storeSeq.eq(qDineReserveStoreInfo.seq))
                .where(bb, eqAdminID(searchType, searchValue), eqAdminName(searchType, searchValue), eqStoreName(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qDineReserveAdmin.seq.count())
                .from(qDineReserveAdmin)
                .leftJoin(qDineReserveStoreInfo).on(qDineReserveAdmin.storeSeq.eq(qDineReserveStoreInfo.seq))
                .where(bb, eqAdminID(searchType, searchValue), eqAdminName(searchType, searchValue), eqStoreName(searchType, searchValue));

        return PageableExecutionUtils.getPage(adminLists, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqAdminID(String searchType, String searchValue) {
        if (!"adminID".equals(searchType) || "".equals(searchValue)) return null;
        return qDineReserveAdmin.adminID.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqAdminName(String searchType, String searchValue) {
        if (!"adminName".equals(searchType) || "".equals(searchValue)) return null;
        return qDineReserveAdmin.adminName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqStoreName(String searchType, String searchValue) {
        if (!"storeName".equals(searchType) || "".equals(searchValue)) return null;
        return qDineReserveStoreInfo.storeName.containsIgnoreCase(searchValue);
    }
}
