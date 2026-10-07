package com.project.dine.reserve.dto.booking;

import com.project.dine.reserve.dto.constant.booking.BookingStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingList {
    private UUID bookingUUID;
    private String storeName;
    private String bookingGuestName;
    private String bookingGuestPhone;
    private int bookingPeopleCount;
    private BookingStatusType bookingStatus;
    private LocalDateTime bookingDate;
    private String bookingMemo;
}
