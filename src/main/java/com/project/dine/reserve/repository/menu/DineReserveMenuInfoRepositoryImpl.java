package com.project.dine.reserve.repository.menu;

import com.project.dine.reserve.domain.menu.QDineReserveMenuCategory;
import com.project.dine.reserve.domain.menu.QDineReserveMenuInfo;
import com.project.dine.reserve.dto.menu.info.MenuInfoListAll;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.project.dine.reserve.util.Common.EMPTY_UUID;

@Repository
public class DineReserveMenuInfoRepositoryImpl implements DineReserveMenuInfoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveMenuInfoRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveMenuCategory qDineReserveMenuCategory = QDineReserveMenuCategory.dineReserveMenuCategory;
    QDineReserveMenuInfo qDineReserveMenuInfo = QDineReserveMenuInfo.dineReserveMenuInfo;

    @Override
    public List<MenuInfoListAll> findMenuInfoListAll(UUID storeUUID, UUID menuCategoryUUID, boolean isTotal) {
        OrderSpecifier<?>[] sortedColumn = {
                qDineReserveMenuCategory.menuCategorySequence.asc(),
                qDineReserveMenuInfo.menuInfoSequence.asc()
        };

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveMenuCategory.storeUUID.eq(storeUUID));
        if (!EMPTY_UUID.equals(menuCategoryUUID)) qDineReserveMenuInfo.menuCategoryUUID.eq(menuCategoryUUID);
        if (!isTotal) qDineReserveMenuInfo.menuInfoVisible.eq(true);

        return jpaQueryFactory
                .select(Projections.fields(
                        MenuInfoListAll.class,
                        qDineReserveMenuInfo.menuInfoUUID.as("menuInfoUUID"),
                        qDineReserveMenuInfo.menuInfoImgUUID.as("menuInfoImgUUID"),
                        qDineReserveMenuInfo.menuInfoName.as("menuInfoName"),
                        qDineReserveMenuInfo.menuInfoPrice.as("menuInfoPrice"),
                        qDineReserveMenuInfo.menuInfoDescription.as("menuInfoDescription"),
                        qDineReserveMenuInfo.menuInfoOrder.as("menuInfoOrder"),
                        qDineReserveMenuInfo.menuInfoVisible.as("menuInfoVisible")
                ))
                .from(qDineReserveMenuInfo)
                .leftJoin(qDineReserveMenuCategory).on(qDineReserveMenuInfo.menuCategorySeq.eq(qDineReserveMenuCategory.seq))
                .where(bb)
                .orderBy(sortedColumn)
                .fetch();
    }
}
