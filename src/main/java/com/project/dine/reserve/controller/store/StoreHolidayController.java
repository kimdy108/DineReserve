package com.project.dine.reserve.controller.store;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayInfo;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayList;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayRegist;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayUpdate;
import com.project.dine.reserve.service.store.StoreHolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/store/holiday")
@Tag(name = "매장 휴일 관리 컨트롤러", description = "매장 휴일 관리 API Controller 입니다.")
public class StoreHolidayController {
    private final StoreHolidayService storeHolidayService;

    @Operation(summary = "store holiday regist", description = "매장 휴일 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> storeHolidayRegist(@RequestBody StoreHolidayRegist storeHolidayRegist) {
        storeHolidayService.storeHolidayRegist(storeHolidayRegist);
        return ResponseEntity.ok(BaseResponse.success("매장 휴일이 등록되었습니다."));
    }

    @Operation(summary = "store holiday update", description = "매장 휴일 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> storeHolidayUpdate(@RequestBody StoreHolidayUpdate storeHolidayUpdate) {
        storeHolidayService.storeHolidayUpdate(storeHolidayUpdate);
        return ResponseEntity.ok(BaseResponse.success("매장 휴일이 수정되었습니다."));
    }

    @Operation(summary = "store holiday delete", description = "매장 휴일 삭제")
    @DeleteMapping("/delete/{holidayUUID}")
    public ResponseEntity<BaseResponse<Void>> storeHolidayDelete(@PathVariable UUID holidayUUID) {
        storeHolidayService.storeHolidayDelete(holidayUUID);
        return ResponseEntity.ok(BaseResponse.success("매장 휴일이 삭제되었습니다."));
    }

    @Operation(summary = "store holiday list", description = "매장 휴일 리스트")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<StoreHolidayList>>> storeHolidayList(
            @RequestParam UUID storeUUID,
            @RequestParam YearMonth yearMonth
    ) {
        var result = storeHolidayService.storeHolidayList(storeUUID, yearMonth);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "store holiday info", description = "매장 휴일 정보")
    @GetMapping("/info/{holidayUUID}")
    public ResponseEntity<BaseResponse<StoreHolidayInfo>> storeHolidayInfo(@PathVariable UUID holidayUUID) {
        var result = storeHolidayService.storeHolidayInfo(holidayUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "매장 휴일 정보가 조회되었습니다."));
    }
}
