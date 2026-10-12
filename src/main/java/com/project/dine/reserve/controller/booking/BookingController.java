package com.project.dine.reserve.controller.booking;

import com.project.dine.reserve.dto.booking.*;
import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/booking")
@Tag(name = "예약 관리 컨트롤러", description = "예약 관리 API Controller 입니다.")
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "booking regist", description = "예약 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> bookingRegist(@RequestBody BookingRegist bookingRegist) {
        bookingService.bookingRegist(bookingRegist);
        return ResponseEntity.ok(BaseResponse.success("예약이 등록되었습니다."));
    }

    @Operation(summary = "booking update", description = "예약 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> bookingUpdate(@RequestBody BookingUpdate bookingUpdate, HttpServletRequest request) {
        bookingService.bookingUpdate(bookingUpdate, request.getHeader("Authorization"));
        return ResponseEntity.ok(BaseResponse.success("예약이 수정되었습니다."));
    }

    @Operation(summary = "booking cancel", description = "예약 취소")
    @PutMapping("/cancel")
    public ResponseEntity<BaseResponse<Void>> bookingCancel(@RequestBody BookingCancel bookingCancel, HttpServletRequest request) {
        bookingService.bookingCancel(bookingCancel, request.getHeader("Authorization"));
        return ResponseEntity.ok(BaseResponse.success("예약이 취소되었습니다."));
    }

    @Operation(summary = "booking status", description = "예약 상태 변경")
    @PutMapping("/status")
    public ResponseEntity<BaseResponse<Void>> bookingStatus(@RequestBody BookingStatus bookingStatus) {
        bookingService.bookingStatus(bookingStatus);
        return ResponseEntity.ok(BaseResponse.success("예약 상태가 변경되었습니다."));
    }

    @Operation(summary = "booking delete", description = "예약 내역 삭제")
    @DeleteMapping("/delete/{bookingUUID}")
    public ResponseEntity<BaseResponse<Void>> bookingDelete(@PathVariable UUID bookingUUID, HttpServletRequest request) {
        bookingService.bookingDelete(bookingUUID, request.getHeader("Authorization"));
        return ResponseEntity.ok(BaseResponse.success("예약 내역이 삭제되었습니다."));
    }

    @Operation(summary = "booking list page", description = "예약 리스트 (페이지)")
    @GetMapping("/list/page")
    public ResponseEntity<BaseResponse<Page<BookingList>>> bookingListPage(
            @RequestParam(required = false) UUID storeUUID,
            @RequestParam(required = false) UUID bookingRegistrantUUID,
            @RequestParam(required = false) LocalDateTime bookingDate,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        var result = bookingService.bookingListPage(storeUUID, bookingRegistrantUUID, bookingDate, offset, limit);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "booking info", description = "예약 정보")
    @GetMapping("/info/{bookingUUID}")
    public ResponseEntity<BaseResponse<BookingInfo>> bookingInfo(@PathVariable UUID bookingUUID) {
        var result = bookingService.bookingInfo(bookingUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "예약 정보가 조회되었습니다."));
    }
}
