package com.project.dine.reserve.controller.store;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailInfo;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailRegist;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailUpdate;
import com.project.dine.reserve.service.store.StoreInfoDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/store/info/detail")
@Tag(name = "매장 상세 관리 컨트롤러", description = "매장 상세 관리 API Controller 입니다.")
public class StoreInfoDetailController {
    private final StoreInfoDetailService storeInfoDetailService;

    @Operation(summary = "store info detail regist", description = "매장 상세 정보 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> storeInfoDetailRegist(@RequestBody StoreInfoDetailRegist storeInfoDetailRegist) {
        storeInfoDetailService.storeInfoDetailRegist(storeInfoDetailRegist);
        return ResponseEntity.ok(BaseResponse.success("매장 상세 정보가 등록되었습니다."));
    }

    @Operation(summary = "store info detail update", description = "매장 상세 정보 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> storeInfoDetailUpdate(@RequestBody StoreInfoDetailUpdate storeInfoDetailUpdate) {
        storeInfoDetailService.storeInfoDetailUpdate(storeInfoDetailUpdate);
        return ResponseEntity.ok(BaseResponse.success("매장 상세 정보가 수정되었습니다."));
    }

    @Operation(summary = "store info detail info", description = "매장 상세 정보")
    @GetMapping("/info/{storeUUID}")
    public ResponseEntity<BaseResponse<StoreInfoDetailInfo>> storeInfoDetailInfo(@PathVariable UUID storeUUID) {
        var result = storeInfoDetailService.storeInfoDetailInfo(storeUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "매장 상세 정보가 조회되었습니다."));
    }
}
