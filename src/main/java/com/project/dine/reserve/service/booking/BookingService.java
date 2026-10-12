package com.project.dine.reserve.service.booking;

import com.project.dine.reserve.config.annotation.RedissonLock;
import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.booking.DineReserveBooking;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.booking.*;
import com.project.dine.reserve.dto.constant.booking.BookingType;
import com.project.dine.reserve.dto.constant.error.BookingErrorCode;
import com.project.dine.reserve.repository.booking.DineReserveBookingRepository;
import com.project.dine.reserve.service.admin.AdminService;
import com.project.dine.reserve.service.member.GuestService;
import com.project.dine.reserve.service.member.MemberService;
import com.project.dine.reserve.service.store.StoreInfoService;
import com.project.dine.reserve.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final JWTUtil jwtUtil;

    private final AdminService adminService;
    private final MemberService memberService;
    private final GuestService guestService;
    private final StoreInfoService storeInfoService;

    private final DineReserveBookingRepository dineReserveBookingRepository;

    @RedissonLock("lock:reservation:#{#bookingRegist.storeUUID}:#{#bookingRegist.bookingDate.toString()}")
    public void bookingRegist(BookingRegist bookingRegist) {
        DineReserveStoreInfo dineReserveStoreInfo = storeInfoService.getStoreInfo(bookingRegist.getStoreUUID());
        Long bookingRegistrantSeq = getBookingRegistrantSeq(bookingRegist.getBookingType(), bookingRegist.getBookingRegistrantUUID());

        DineReserveBooking dineReserveBooking = DineReserveBooking.create(bookingRegist, bookingRegistrantSeq, dineReserveStoreInfo);
        dineReserveBookingRepository.save(dineReserveBooking);
    }

    @RedissonLock("lock:reservation:#{#bookingUpdate.storeUUID}:#{#bookingUpdate.bookingDate.toString()}")
    public void bookingUpdate(BookingUpdate bookingUpdate, String token) {
        DineReserveBooking dineReserveBooking = getBooking(bookingUpdate.getBookingUUID());

        if (!bookingBasicValid(dineReserveBooking, token)) throw new DineReserveException(BookingErrorCode.NOT_VALID_UPDATE);

        dineReserveBooking.update(bookingUpdate);
    }

    @Transactional
    public void bookingCancel(BookingCancel bookingCancel, String token) {
        DineReserveBooking dineReserveBooking = getBooking(bookingCancel.getBookingUUID());

        if (!bookingBasicValid(dineReserveBooking, token)) throw new DineReserveException(BookingErrorCode.NOT_VALID_CANCEL);

        dineReserveBooking.cancel(bookingCancel, getBookingRegistrantSeq(bookingCancel.getCancelType(), bookingCancel.getCancelRegistrantUUID()));
    }

    @Transactional
    public void bookingStatus(BookingStatus bookingStatus) {
        DineReserveBooking dineReserveBooking = getBooking(bookingStatus.getBookingUUID());
        dineReserveBooking.updateStatus(bookingStatus.getBookingStatus());
    }

    @Transactional
    public void bookingDelete(UUID bookingUUID, String token) {
        if (!bookingAdminValid(token)) throw new DineReserveException(BookingErrorCode.NOT_VALID_DELETE);

        DineReserveBooking dineReserveBooking = getBooking(bookingUUID);
        dineReserveBooking.updateUseFlag(false);
    }

    public Page<BookingList> bookingListPage(UUID storeUUID, UUID bookingRegistrantUUID, LocalDateTime bookingDate, Long offset, int limit) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return dineReserveBookingRepository.findBookingListPage(storeUUID, bookingRegistrantUUID, bookingDate, offset, limit, pageable);
    }

    public BookingInfo bookingInfo(UUID bookingUUID) {
        DineReserveBooking dineReserveBooking = getBooking(bookingUUID);
        DineReserveStoreInfo dineReserveStoreInfo = storeInfoService.getStoreInfo(dineReserveBooking.getStoreUUID());
        String bookingRegistrantName = getBookingTypeName(dineReserveBooking.getBookingType(), dineReserveBooking.getBookingRegistrantUUID());
        String cancelRegistrantName = getBookingTypeName(dineReserveBooking.getCancelType(), dineReserveBooking.getCancelRegistrantUUID());

        return BookingInfo.create(dineReserveBooking, dineReserveStoreInfo.getStoreName(), bookingRegistrantName, cancelRegistrantName);
    }

    private DineReserveBooking getBooking(UUID bookingUUID) {
        return dineReserveBookingRepository.findByBookingUUID(bookingUUID)
                .orElseThrow(() -> new DineReserveException(BookingErrorCode.NO_BOOKING));
    }

    private Long getBookingRegistrantSeq(BookingType bookingType, UUID bookingRegistrantUUID) {
        return switch (bookingType) {
            case ADMIN -> adminService.getAdmin(bookingRegistrantUUID).getSeq();
            case MEMBER -> memberService.getMember(bookingRegistrantUUID).getSeq();
            case GUEST -> guestService.getGuest(bookingRegistrantUUID).getSeq();
            case NONE -> throw new DineReserveException(BookingErrorCode.NOT_VALID_TYPE);
        };
    }

    private String getBookingTypeName(BookingType bookingType, UUID bookinTypeUUID) {
        return switch (bookingType) {
            case ADMIN -> adminService.getAdmin(bookinTypeUUID).getAdminName();
            case MEMBER -> memberService.getMember(bookinTypeUUID).getMemberName();
            case GUEST -> guestService.getGuest(bookinTypeUUID).getGuestName();
            case NONE -> "";
        };
    }

    private boolean bookingBasicValid(DineReserveBooking dineReserveBooking, String token) {
        UUID actorUUID = jwtUtil.getUserUUID(token.replace("Bearer ", ""));
        return dineReserveBooking.getBookingRegistrantUUID().equals(actorUUID) || bookingAdminValid(token);
    }

    private boolean bookingAdminValid(String token) {
        return jwtUtil.isAdmin(token.replace("Bearer ", ""));
    }
}
