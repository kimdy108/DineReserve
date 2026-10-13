package com.project.dine.reserve.validator;

import com.project.dine.reserve.domain.booking.DineReserveBooking;
import com.project.dine.reserve.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingValidator {
    private final JWTUtil jwtUtil;

    public boolean checkSlotValid(boolean isUseBreakTime, LocalDateTime startDate, LocalDateTime slotEndDate, LocalDateTime breakStartDate, LocalDateTime breakEndDate, LocalDateTime nowDate) {
        boolean isBreak = isUseBreakTime && startDate.isBefore(breakEndDate) && slotEndDate.isAfter(breakStartDate);
        boolean isPast = startDate.isBefore(nowDate);

        return !isBreak && !isPast;
    }

    public boolean checkUserValid(DineReserveBooking dineReserveBooking, String token) {
        UUID actorUUID = jwtUtil.getUserUUID(token.replace("Bearer ", ""));
        return dineReserveBooking.getBookingRegistrantUUID().equals(actorUUID) || checkAdminValid(token);
    }

    public boolean checkAdminValid(String token) {
        return jwtUtil.isAdmin(token.replace("Bearer ", ""));
    }
}
