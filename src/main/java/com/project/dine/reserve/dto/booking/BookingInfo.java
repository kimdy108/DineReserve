package com.project.dine.reserve.dto.booking;

import com.project.dine.reserve.domain.booking.DineReserveBooking;
import com.project.dine.reserve.dto.constant.booking.BookingStatusType;
import com.project.dine.reserve.dto.constant.booking.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfo {
    private UUID bookingUUID;
    private UUID storeUUID;
    private String storeName;
    private UUID bookingRegistrantUUID;
    private String bookingRegistrantName;
    private String bookingGuestName;
    private String bookingGuestPhone;
    private int bookingPeopleCount;
    private BookingStatusType bookingStatus;
    private BookingType bookingType;
    private LocalDateTime bookingDate;
    private String bookingMemo;
    private UUID cancelRegistrantUUID;
    private String cancelRegistrantName;
    private LocalDateTime cancelDate;

    public static BookingInfo create(DineReserveBooking dineReserveBooking, String storeName, String bookingRegistrantName, String cancelRegistrantName) {
        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setBookingUUID(dineReserveBooking.getBookingUUID());
        bookingInfo.setStoreUUID(dineReserveBooking.getStoreUUID());
        bookingInfo.setStoreName(storeName);
        bookingInfo.setBookingRegistrantUUID(dineReserveBooking.getBookingRegistrantUUID());
        bookingInfo.setBookingRegistrantName(bookingRegistrantName);
        bookingInfo.setBookingGuestName(dineReserveBooking.getBookingGuestName());
        bookingInfo.setBookingGuestPhone(dineReserveBooking.getBookingGuestPhone());
        bookingInfo.setBookingPeopleCount(dineReserveBooking.getBookingPeopleCount());
        bookingInfo.setBookingStatus(dineReserveBooking.getBookingStatus());
        bookingInfo.setBookingType(dineReserveBooking.getBookingType());
        bookingInfo.setBookingDate(dineReserveBooking.getBookingDate());
        bookingInfo.setBookingMemo(dineReserveBooking.getBookingMemo());
        bookingInfo.setCancelRegistrantUUID(dineReserveBooking.getCancelRegistrantUUID());
        bookingInfo.setCancelRegistrantName(cancelRegistrantName);
        bookingInfo.setCancelDate(dineReserveBooking.getCancelDate());

        return bookingInfo;
    }
}
