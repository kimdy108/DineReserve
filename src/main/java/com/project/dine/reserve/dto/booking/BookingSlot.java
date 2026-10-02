package com.project.dine.reserve.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingSlot {
    private LocalDateTime bookingDateTime;
    private int bookingRemaining;
    private boolean bookingStatus;

    public static BookingSlot create(LocalDateTime bookingDateTime, int bookingRemaining, boolean bookingStatus) {
        BookingSlot bookingSlot = new BookingSlot();
        bookingSlot.setBookingDateTime(bookingDateTime);
        bookingSlot.setBookingRemaining(bookingRemaining);
        bookingSlot.setBookingStatus(bookingStatus);

        return bookingSlot;
    }
}
