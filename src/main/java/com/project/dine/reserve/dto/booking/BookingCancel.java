package com.project.dine.reserve.dto.booking;

import com.project.dine.reserve.dto.constant.booking.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCancel {
    private UUID bookingUUID;
    private BookingType cancelType;
    private UUID cancelRegistrantUUID;
}
