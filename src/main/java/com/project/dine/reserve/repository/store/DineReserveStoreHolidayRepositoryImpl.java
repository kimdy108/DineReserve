package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.QDineReserveStoreHoliday;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Repository
public class DineReserveStoreHolidayRepositoryImpl implements DineReserveStoreHolidayRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveStoreHolidayRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveStoreHoliday qDineReserveStoreHoliday = QDineReserveStoreHoliday.dineReserveStoreHoliday;

    @Override
    public List<StoreHolidayList> findStoreHolidayList(UUID storeUUID, YearMonth yearMonth) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveStoreHoliday.storeUUID.eq(storeUUID));
        bb.and(eqNormalHoliday(yearMonth).or(eqRepeatHoliday(yearMonth)));

        return jpaQueryFactory
                .select(Projections.fields(
                        StoreHolidayList.class,
                        qDineReserveStoreHoliday.holidayUUID.as("holidayUUID"),
                        qDineReserveStoreHoliday.holidayDate.as("holidayDate"),
                        qDineReserveStoreHoliday.holidayReason.as("holidayReason"),
                        qDineReserveStoreHoliday.isRepeat.as("isRepeat")
                ))
                .from(qDineReserveStoreHoliday)
                .where(bb)
                .fetch();
    }

    private BooleanExpression eqNormalHoliday(YearMonth yearMonth) {
        return qDineReserveStoreHoliday.isRepeat.eq(false)
                .and(qDineReserveStoreHoliday.holidayDate.year().eq(yearMonth.getYear()))
                .and(qDineReserveStoreHoliday.holidayDate.month().eq(yearMonth.getMonthValue()));
    }

    private BooleanExpression eqRepeatHoliday(YearMonth yearMonth) {
        return qDineReserveStoreHoliday.isRepeat.eq(true)
                .and(qDineReserveStoreHoliday.holidayDate.month().eq(yearMonth.getMonthValue()));
    }
}
