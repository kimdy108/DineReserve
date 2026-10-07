package com.project.dine.reserve.dto.booking;

import com.project.dine.reserve.dto.constant.booking.BookingStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatus {
    private UUID bookingUUID;
    private BookingStatusType bookingStatus;
}
