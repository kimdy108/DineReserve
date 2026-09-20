package com.project.dine.reserve.controller.store;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.store.schedule.StoreInfoSchedule;
import com.project.dine.reserve.dto.store.schedule.StoreInfoScheduleRegist;
import com.project.dine.reserve.dto.store.schedule.StoreInfoScheduleUpdate;
import com.project.dine.reserve.service.store.StoreInfoScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/store/info/schedule")
@Tag(name = "매장 운영시간 관리 컨트롤러", description = "매장 운영시간 관리 API Controller 입니다.")
public class StoreInfoScheduleController {
    private final StoreInfoScheduleService storeInfoScheduleService;

    @Operation(summary = "store info schedule regist", description = "매장 운영시간 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> storeInfoScheduleRegist(@RequestBody StoreInfoScheduleRegist storeInfoScheduleRegist) {
        storeInfoScheduleService.storeInfoScheduleRegist(storeInfoScheduleRegist);
        return ResponseEntity.ok(BaseResponse.success("매장 운영시간이 등록되었습니다."));
    }

    @Operation(summary = "store info schedule update", description = "매장 운영시간 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> storeInfoScheduleUpdate(@RequestBody StoreInfoScheduleUpdate storeInfoScheduleUpdate) {
        storeInfoScheduleService.storeInfoScheduleUpdate(storeInfoScheduleUpdate);
        return ResponseEntity.ok(BaseResponse.success("매장 운영시간이 수정되었습니다."));
    }

    @Operation(summary = "store info schedule list all", description = "매장 운영시간 리스트 (전체)")
    @GetMapping("/list/all/{storeUUID}")
    public ResponseEntity<BaseResponse<List<StoreInfoSchedule>>> storeInfoScheduleListAll(@PathVariable UUID storeUUID) {
        var result = storeInfoScheduleService.storeInfoScheduleListAll(storeUUID);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}
