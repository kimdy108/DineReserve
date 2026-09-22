package com.project.dine.reserve.controller.menu;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.menu.info.*;
import com.project.dine.reserve.service.menu.MenuInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/menu/info")
@Tag(name = "매뉴 관리 컨트롤러", description = "메뉴 관리 API Controller 입니다.")
public class MenuInfoController {
    private final MenuInfoService menuInfoService;

    @Operation(summary = "menu info regist", description = "메뉴 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> menuInfoRegist(@ModelAttribute MenuInfoRegist menuInfoRegist) {
        menuInfoService.menuInfoRegist(menuInfoRegist);
        return ResponseEntity.ok(BaseResponse.success("메뉴가 등록되었습니다."));
    }

    @Operation(summary = "menu info update", description = "메뉴 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> menuInfoUpdate(@ModelAttribute MenuInfoUpdate menuInfoUpdate) {
        menuInfoService.menuInfoUpdate(menuInfoUpdate);
        return ResponseEntity.ok(BaseResponse.success("메뉴가 수정되었습니다."));
    }

    @Operation(summary = "menu info delete", description = "메뉴 삭제")
    @DeleteMapping("/delete/{menuInfoUUID}")
    public ResponseEntity<BaseResponse<Void>> menuInfoDelete(@PathVariable UUID menuInfoUUID) {
        menuInfoService.menuInfoDelete(menuInfoUUID);
        return ResponseEntity.ok(BaseResponse.success("메뉴가 삭제되었습니다."));
    }

    @Operation(summary = "menu info sequence", description = "메뉴 순서")
    @PutMapping("/sequence")
    public ResponseEntity<BaseResponse<Void>> menuInfoSequence(@RequestBody MenuInfoSequenceUpdate menuInfoSequenceUpdate) {
        menuInfoService.menuInfoSequence(menuInfoSequenceUpdate);
        return ResponseEntity.ok(BaseResponse.success("메뉴 순서가 수정되었습니다."));
    }

    @Operation(summary = "menu info list all", description = "메뉴 리스트 (전체)")
    @GetMapping("/list/all")
    public ResponseEntity<BaseResponse<List<MenuInfoListAll>>> menuInfoListAll(
            @RequestParam UUID storeUUID,
            @RequestParam(required = false) UUID menuCategoryUUID
    ) {
        var result = menuInfoService.menuInfoListAll(storeUUID, menuCategoryUUID);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "menu info list total", description = "메뉴 리스트 (전체 - false 포함)")
    @GetMapping("/list/total")
    public ResponseEntity<BaseResponse<List<MenuInfoListAll>>> menuInfoListTotal(
            @RequestParam UUID storeUUID,
            @RequestParam(required = false) UUID menuCategoryUUID
    ) {
        var result = menuInfoService.menuInfoListTotal(storeUUID, menuCategoryUUID);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "menu info info", description = "메뉴 정보")
    @GetMapping("/info/{menuInfoUUID}")
    public ResponseEntity<BaseResponse<MenuInfoInfo>> menuInfoInfo(@PathVariable UUID menuInfoUUID) {
        var result = menuInfoService.menuInfoInfo(menuInfoUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "메뉴 정보가 조회되었습니다."));
    }
}
