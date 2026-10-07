package com.project.dine.reserve.repository.booking;

import com.project.dine.reserve.domain.booking.QDineReserveBooking;
import com.project.dine.reserve.domain.store.QDineReserveStoreInfo;
import com.project.dine.reserve.dto.booking.BookingList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class DineReserveBookingRepositoryImpl implements DineReserveBookingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveBookingRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveStoreInfo qDineReserveStoreInfo = QDineReserveStoreInfo.dineReserveStoreInfo;
    QDineReserveBooking qDineReserveBooking = QDineReserveBooking.dineReserveBooking;

    @Override
    public Page<BookingList> findBookingListPage(UUID storeUUID, UUID bookingRegistrantUUID, LocalDateTime bookingDate, Long offset, int limit, Pageable pageable) {
        Long setOffset = offset * limit;

        OrderSpecifier<?> sortedColumn = qDineReserveBooking.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        if (storeUUID != null) bb.and(qDineReserveStoreInfo.storeUUID.eq(storeUUID));
        if (bookingRegistrantUUID != null) bb.and(qDineReserveBooking.bookingRegistrantUUID.eq(bookingRegistrantUUID));
        if (bookingDate != null) bb.and(qDineReserveBooking.bookingDate.eq(bookingDate));

        List<BookingList> bookingLists = jpaQueryFactory
                .select(Projections.fields(
                        BookingList.class,
                        qDineReserveBooking.bookingUUID.as("bookingUUID"),
                        qDineReserveStoreInfo.storeName.as("storeName"),
                        qDineReserveBooking.bookingGuestName.as("bookingGuestName"),
                        qDineReserveBooking.bookingGuestPhone.as("bookingGuestPhone"),
                        qDineReserveBooking.bookingPeopleCount.as("bookingPeopleCount"),
                        qDineReserveBooking.bookingStatus.as("bookingStatus"),
                        qDineReserveBooking.bookingDate.as("bookingDate"),
                        qDineReserveBooking.bookingMemo.as("bookingMemo")
                ))
                .from(qDineReserveBooking)
                .leftJoin(qDineReserveStoreInfo).on(qDineReserveBooking.storeSeq.eq(qDineReserveStoreInfo.seq))
                .where(bb)
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qDineReserveBooking.seq.count())
                .from(qDineReserveBooking)
                .leftJoin(qDineReserveStoreInfo).on(qDineReserveBooking.storeSeq.eq(qDineReserveStoreInfo.seq))
                .where(bb);

        return PageableExecutionUtils.getPage(bookingLists, pageable, countQuery::fetchOne);
    }
}
