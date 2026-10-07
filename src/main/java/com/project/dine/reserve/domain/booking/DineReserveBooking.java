package com.project.dine.reserve.domain.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.booking.BookingCancel;
import com.project.dine.reserve.dto.booking.BookingRegist;
import com.project.dine.reserve.dto.booking.BookingUpdate;
import com.project.dine.reserve.dto.constant.booking.BookingStatusType;
import com.project.dine.reserve.dto.constant.booking.BookingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.dine.reserve.util.Common.EMPTY_SEQ;
import static com.project.dine.reserve.util.Common.EMPTY_UUID;

@Entity
@Table(name = "dine_reserve_booking", indexes = {
        @Index(name = "idx_booking_uuid", columnList = "booking_uuid"),
        @Index(name = "idx_store_seq", columnList = "store_seq"),
        @Index(name = "idx_store_uuid", columnList = "store_uuid"),
        @Index(name = "idx_booking_registrant_seq", columnList = "booking_registrant_seq"),
        @Index(name = "idx_booking_registrant_uuid", columnList = "booking_registrant_uuid"),
        @Index(name = "idx_booking_guest_name", columnList = "booking_guest_name"),
        @Index(name = "idx_cancel_registrant_seq", columnList = "cancel_registrant_seq"),
        @Index(name = "idx_cancel_registrant_uuid", columnList = "cancel_registrant_uuid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveBooking extends DineReserveBase {
    @Comment("예약 UUID")
    @Column(name = "booking_uuid", length = 50, nullable = false, unique = true)
    private UUID bookingUUID;

    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false)
    private UUID storeUUID;

    @Comment("예약자 SEQ")
    @Column(name = "booking_registrant_seq", length = 20, nullable = false)
    private Long bookingRegistrantSeq;

    @Comment("예약자 UUID")
    @Column(name = "booking_registrant_uuid", length = 50, nullable = false)
    private UUID bookingRegistrantUUID;

    @Comment("이용자 이름")
    @Column(name = "booking_guest_name", length = 50, nullable = false)
    private String bookingGuestName;

    @Comment("이용자 전화번호")
    @Column(name = "booking_guest_phone", length = 50, nullable = false)
    private String bookingGuestPhone;

    @Comment("예약 인원 수")
    @Column(name = "booking_people_count", length = 10, nullable = false)
    private int bookingPeopleCount;

    @Comment("예약 상태")
    @Column(name = "booking_status", columnDefinition = "ENUM('BOOKING','CANCEL','COMPLETE','NO_SHOW') NOT NULL DEFAULT 'BOOKING'")
    @Enumerated(EnumType.STRING)
    private BookingStatusType bookingStatus;

    @Comment("예약 타입")
    @Column(name = "booking_type", columnDefinition = "ENUM('ADMIN','MEMBER','GUEST') NOT NULL DEFAULT 'GUEST'")
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @Comment("예약 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "booking_date", columnDefinition = "DATETIME")
    private LocalDateTime bookingDate;

    @Comment("요청 사항")
    @Column(name = "booking_memo", columnDefinition = "TEXT")
    private String bookingMemo;

    @Comment("취소자 SEQ")
    @Column(name = "cancel_registrant_seq", length = 20, nullable = false)
    private Long cancelRegistrantSeq;

    @Comment("취소자 UUID")
    @Column(name = "cancel_registrant_uuid", length = 50, nullable = false)
    private UUID cancelRegistrantUUID;

    @Comment("취소 타입")
    @Column(name = "cancel_type", columnDefinition = "ENUM('ADMIN','MEMBER','GUEST','NONE') NOT NULL DEFAULT 'NONE'")
    @Enumerated(EnumType.STRING)
    private BookingType cancelType;

    @Comment("취소 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "cancel_date", columnDefinition = "DATETIME")
    private LocalDateTime cancelDate;

    public static DineReserveBooking create(BookingRegist bookingRegist, Long bookingRegistrantSeq, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveBooking dineReserveBooking = new DineReserveBooking();
        dineReserveBooking.setBookingUUID(UUID.randomUUID());
        dineReserveBooking.setStoreSeq(dineReserveStoreInfo.getSeq());
        dineReserveBooking.setStoreUUID(dineReserveStoreInfo.getStoreUUID());
        dineReserveBooking.setBookingRegistrantSeq(bookingRegistrantSeq);
        dineReserveBooking.setBookingRegistrantUUID(bookingRegist.getBookingRegistrantUUID());
        dineReserveBooking.setBookingGuestName(bookingRegist.getBookingGuestName());
        dineReserveBooking.setBookingGuestPhone(bookingRegist.getBookingGuestPhone());
        dineReserveBooking.setBookingPeopleCount(bookingRegist.getBookingPeopleCount());
        dineReserveBooking.setBookingStatus(BookingStatusType.BOOKING);
        dineReserveBooking.setBookingType(bookingRegist.getBookingType());
        dineReserveBooking.setBookingDate(bookingRegist.getBookingDate());
        dineReserveBooking.setBookingMemo(bookingRegist.getBookingMemo());
        dineReserveBooking.setCancelRegistrantSeq(EMPTY_SEQ);
        dineReserveBooking.setCancelRegistrantUUID(EMPTY_UUID);
        dineReserveBooking.setCancelType(BookingType.NONE);
        dineReserveBooking.setCancelDate(LocalDateTime.parse("1970-01-01T00:00:00"));

        dineReserveBooking.setUseFlag(true);
        dineReserveBooking.setInsertDate(LocalDateTime.now());
        dineReserveBooking.setUpdateDate(LocalDateTime.now());

        return dineReserveBooking;
    }

    public void update(BookingUpdate bookingUpdate) {
        this.bookingGuestName = bookingUpdate.getBookingGuestName();
        this.bookingGuestPhone = bookingUpdate.getBookingGuestPhone();
        this.bookingPeopleCount = bookingUpdate.getBookingPeopleCount();
        this.bookingDate = bookingUpdate.getBookingDate();
        this.bookingMemo = bookingUpdate.getBookingMemo();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void cancel(BookingCancel bookingCancel, Long cancelRegistrantSeq) {
        this.bookingStatus = BookingStatusType.CANCEL;
        this.cancelRegistrantSeq = cancelRegistrantSeq;
        this.cancelRegistrantUUID = bookingCancel.getCancelRegistrantUUID();
        this.cancelType = bookingCancel.getCancelType();
        this.cancelDate = LocalDateTime.now();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(BookingStatusType bookingStatus) {
        this.bookingStatus = bookingStatus;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateUseFlag(boolean useFlag) {
        this.setUseFlag(useFlag);
        this.setUpdateDate(LocalDateTime.now());
    }
}
