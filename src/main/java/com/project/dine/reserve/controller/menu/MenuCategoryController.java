package com.project.dine.reserve.controller.menu;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.menu.category.*;
import com.project.dine.reserve.service.menu.MenuCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/menu/category")
@Tag(name = "메뉴 카테고리 관리 컨트롤러", description = "메뉴 카테고리 관리 API Controller 입니다.")
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    @Operation(summary = "menu category regist", description = "메뉴 카테고리 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> menuCategoryRegist(@RequestBody MenuCategoryRegist menuCategoryRegist) {
        menuCategoryService.menuCategoryRegist(menuCategoryRegist);
        return ResponseEntity.ok(BaseResponse.success("메뉴 카테고리가 등록되었습니다."));
    }

    @Operation(summary = "menu category update", description = "메뉴 카테고리 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> menuCategoryUpdate(@RequestBody MenuCategoryUpdate menuCategoryUpdate) {
        menuCategoryService.menuCategoryUpdate(menuCategoryUpdate);
        return ResponseEntity.ok(BaseResponse.success("메뉴 카테고리가 수정되었습니다."));
    }

    @Operation(summary = "menu category delete", description = "메뉴 카테고리 삭제")
    @DeleteMapping("/delete/{menuCategoryUUID}")
    public ResponseEntity<BaseResponse<Void>> menuCategoryDelete(@PathVariable UUID menuCategoryUUID) {
        menuCategoryService.menuCategoryDelete(menuCategoryUUID);
        return ResponseEntity.ok(BaseResponse.success("메뉴 카테고리가 삭제되었습니다."));
    }

    @Operation(summary = "menu category active", description = "메뉴 카테고리 활성화 / 비활성화")
    @PutMapping("/active")
    public ResponseEntity<BaseResponse<Void>> menuCategoryActive(@RequestBody MenuCategoryActive menuCategoryActive) {
        menuCategoryService.menuCategoryActive(menuCategoryActive);
        return ResponseEntity.ok(BaseResponse.success("메뉴 카테고리 활성화 / 비활성화 여부가 수정되었습니다."));
    }

    @Operation(summary = "menu category sequence", description = "메뉴 카테고리 순서")
    @PutMapping("/sequence")
    public ResponseEntity<BaseResponse<Void>> menuCategorySequence(@RequestBody MenuCategorySequenceUpdate menuCategorySequenceUpdate) {
        menuCategoryService.menuCategorySequence(menuCategorySequenceUpdate);
        return ResponseEntity.ok(BaseResponse.success("메뉴 카테고리 순서가 수정되었습니다."));
    }

    @Operation(summary = "menu category list all", description = "메뉴 카테고리 리스트 (전체)")
    @GetMapping("/list/all/{storeUUID}")
    public ResponseEntity<BaseResponse<List<MenuCategoryListAll>>> menuCategoryListAll(@PathVariable UUID storeUUID) {
        var result = menuCategoryService.menuCategoryListAll(storeUUID);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}
