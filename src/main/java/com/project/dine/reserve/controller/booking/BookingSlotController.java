package com.project.dine.reserve.controller.booking;

import com.project.dine.reserve.dto.booking.BookingSlot;
import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.booking.BookingSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/booking/slot")
@Tag(name = "예약 슬롯 관리 컨트롤러", description = "예약 슬롯 관리 API Controller 입니다.")
public class BookingSlotController {
    private final BookingSlotService bookingSlotService;

    @Operation(summary = "create booking slot", description = "예약 슬롯 생성")
    @GetMapping("/create")
    public ResponseEntity<BaseResponse<List<BookingSlot>>> createBookingSlot(
            @RequestParam UUID storeUUID,
            @RequestParam LocalDate bookingDate
    ) {
        var result = bookingSlotService.createBookingSlot(storeUUID, bookingDate);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}
