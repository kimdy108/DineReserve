package com.project.dine.reserve.repository.booking;

import com.project.dine.reserve.dto.booking.BookingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface DineReserveBookingRepositoryCustom {
    Page<BookingList> findBookingListPage(UUID storeUUID, UUID bookingRegistrantUUID, LocalDateTime bookingDate, Long offset, int limit, Pageable pageable);
}
