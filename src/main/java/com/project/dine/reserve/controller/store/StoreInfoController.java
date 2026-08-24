package com.project.dine.reserve.controller.store;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.store.info.*;
import com.project.dine.reserve.service.store.StoreInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/store/info")
@Tag(name = "매장 관리 컨트롤러", description = "매장 관리 API Controller 입니다.")
public class StoreInfoController {
    private final StoreInfoService storeInfoService;

    @Operation(summary = "store info regist", description = "매장 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> storeInfoRegist(@ModelAttribute StoreInfoRegist storeInfoRegist) {
        storeInfoService.storeInfoRegist(storeInfoRegist);
        return ResponseEntity.ok(BaseResponse.success("매장이 등록되었습니다."));
    }

    @Operation(summary = "store info update", description = "매장 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> storeInfoUpdate(@ModelAttribute StoreInfoUpdate storeInfoUpdate) {
        storeInfoService.storeInfoUpdate(storeInfoUpdate);
        return ResponseEntity.ok(BaseResponse.success("매장 정보가 수정되었습니다."));
    }

    @Operation(summary = "store info delete", description = "매장 삭제")
    @DeleteMapping("/delete/{storeInfoUUID}")
    public ResponseEntity<BaseResponse<Void>> storeInfoDelete(@PathVariable UUID storeInfoUUID) {
        storeInfoService.storeInfoDelete(storeInfoUUID);
        return ResponseEntity.ok(BaseResponse.success("매장이 삭제되었습니다."));
    }

    @Operation(summary = "store info active", description = "매장 활성화 / 비활성화")
    @PutMapping("/active")
    public ResponseEntity<BaseResponse<Void>> storeInfoActive(@RequestBody StoreInfoActive storeInfoActive) {
        storeInfoService.storeInfoActive(storeInfoActive);
        return ResponseEntity.ok(BaseResponse.success("매장 활성화 / 비활성화 여부가 수정되었습니다."));
    }

    @Operation(summary = "store info list page", description = "매장 리스트 (페이지)")
    @GetMapping("/list/page")
    public ResponseEntity<BaseResponse<Page<StoreInfoList>>> storeInfoListPage(
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) String categoryUUID,
            @RequestParam(required = false) String storeStatus,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        var result = storeInfoService.storeInfoListPage(searchType, searchValue, categoryUUID, storeStatus, offset, limit);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "store info list all", description = "매장 리스트 (전체)")
    @GetMapping("/list/all")
    public ResponseEntity<BaseResponse<List<StoreInfoListAll>>> storeInfoListAll() {
        var result = storeInfoService.storeInfoListAll();
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "store info info", description = "매장 정보")
    @GetMapping("/info/{storeInfoUUID}")
    public ResponseEntity<BaseResponse<StoreInfoInfo>> storeInfoInfo(@PathVariable UUID storeInfoUUID) {
        var result = storeInfoService.storeInfoInfo(storeInfoUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "매장 정보가 조회되었습니다."));
    }
}
