package com.project.dine.reserve.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdate {
    private UUID bookingUUID;
    private UUID storeUUID;
    private String bookingGuestName;
    private String bookingGuestPhone;
    private int bookingPeopleCount;
    private LocalDateTime bookingDate;
    private String bookingMemo;
}
