package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.QDineReserveStoreInfoSchedule;
import com.project.dine.reserve.dto.store.schedule.StoreInfoSchedule;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DineReserveStoreInfoScheduleRepositoryImpl implements DineReserveStoreInfoScheduleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveStoreInfoScheduleRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveStoreInfoSchedule qDineReserveStoreInfoSchedule = QDineReserveStoreInfoSchedule.dineReserveStoreInfoSchedule;

    @Override
    public List<StoreInfoSchedule> findStoreInfoScheduleListByStoreUUID(UUID storeUUID) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveStoreInfoSchedule.storeUUID.eq(storeUUID));

        return jpaQueryFactory
                .select(Projections.fields(
                        StoreInfoSchedule.class,
                        qDineReserveStoreInfoSchedule.dayOfWeek.as("dayOfWeek"),
                        qDineReserveStoreInfoSchedule.workStartTime.as("workStartTime"),
                        qDineReserveStoreInfoSchedule.workEndTime.as("workEndTime"),
                        qDineReserveStoreInfoSchedule.storeOff.as("storeOff"),
                        qDineReserveStoreInfoSchedule.useBreakTime.as("useBreakTime"),
                        qDineReserveStoreInfoSchedule.breakStartTime.as("breakStartTime"),
                        qDineReserveStoreInfoSchedule.breakEndTime.as("breakEndTime")
                ))
                .from(qDineReserveStoreInfoSchedule)
                .where(bb)
                .fetch();
    }
}
