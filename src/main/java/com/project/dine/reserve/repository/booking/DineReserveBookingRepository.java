package com.project.dine.reserve.repository.booking;

import com.project.dine.reserve.domain.booking.DineReserveBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveBookingRepository extends JpaRepository<DineReserveBooking, Long>, DineReserveBookingRepositoryCustom {
    Optional<DineReserveBooking> findByBookingUUID(UUID bookingUUID);
}
