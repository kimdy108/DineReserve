package com.project.dine.reserve.dto.booking;

import com.project.dine.reserve.dto.constant.booking.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRegist {
    private UUID bookingRegistrantUUID;
    private UUID storeUUID;
    private String bookingGuestName;
    private String bookingGuestPhone;
    private int bookingPeopleCount;
    private BookingType bookingType;
    private LocalDateTime bookingDate;
    private String bookingMemo;
}
