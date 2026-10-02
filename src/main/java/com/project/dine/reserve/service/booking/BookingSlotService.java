package com.project.dine.reserve.service.booking;

import com.project.dine.reserve.domain.store.DineReserveStoreInfoDetail;
import com.project.dine.reserve.domain.store.DineReserveStoreInfoSchedule;
import com.project.dine.reserve.dto.booking.BookingSlot;
import com.project.dine.reserve.service.store.StoreInfoDetailService;
import com.project.dine.reserve.service.store.StoreInfoScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingSlotService {
    private final StoreInfoDetailService storeInfoDetailService;
    private final StoreInfoScheduleService storeInfoScheduleService;

    public List<BookingSlot> createBookingSlot(UUID storeUUID, LocalDate bookingDate) {
        DineReserveStoreInfoDetail dineReserveStoreInfoDetail = storeInfoDetailService.getStoreInfoDetail(storeUUID);
        DineReserveStoreInfoSchedule dineReserveStoreInfoSchedule = storeInfoScheduleService.getStoreInfoSchedule(dineReserveStoreInfoDetail.getStoreUUID(), bookingDate.getDayOfWeek());

        if (dineReserveStoreInfoSchedule.isStoreOff()) return List.of();

        LocalDateTime nowDate = LocalDateTime.now();

        List<BookingSlot> bookingSlots = new ArrayList<>();

        int unit = dineReserveStoreInfoDetail.getReserveTimeUnit();

        LocalDateTime startDate = LocalDate.from(nowDate).atTime(dineReserveStoreInfoSchedule.getWorkStartTime());
        LocalDateTime endDate = LocalDate.from(nowDate).atTime(dineReserveStoreInfoSchedule.getWorkEndTime());
        if (!startDate.isBefore(endDate)) endDate = endDate.plusDays(1);

        LocalDateTime breakStartDate = LocalDate.from(nowDate).atTime(dineReserveStoreInfoSchedule.getBreakStartTime());
        LocalDateTime breakEndDate = LocalDate.from(nowDate).atTime(dineReserveStoreInfoSchedule.getBreakEndTime());

        while (!startDate.plusMinutes(unit).isAfter(endDate)) {
            LocalDateTime slotEndDate = startDate.plusMinutes(unit);

            boolean isSlotValid = checkSlotValid(dineReserveStoreInfoSchedule.isUseBreakTime(), startDate, slotEndDate, breakStartDate, breakEndDate, nowDate);
            if (isSlotValid) bookingSlots.add(BookingSlot.create(startDate, 0, true)); // todo booking count check
            else bookingSlots.add(BookingSlot.create(startDate, 0, false));

            startDate = slotEndDate;
        }

        return bookingSlots;
    }

    private boolean checkSlotValid(boolean isUseBreakTime, LocalDateTime startDate, LocalDateTime slotEndDate, LocalDateTime breakStartDate, LocalDateTime breakEndDate, LocalDateTime nowDate) {
        boolean isBreak = isUseBreakTime && startDate.isBefore(breakEndDate) && slotEndDate.isAfter(breakStartDate);
        boolean isPast = startDate.isBefore(nowDate);

        return !isBreak && !isPast;
    }
}
