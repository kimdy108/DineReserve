package com.project.dine.reserve.controller.store;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.store.category.*;
import com.project.dine.reserve.service.store.StoreCategoryService;
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
@RequestMapping("/api/dine/reserve/store/category")
@Tag(name = "매장 카테고리 관리 컨트롤러", description = "매장 카테고리 관리 API Controller 입니다.")
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    @Operation(summary = "category regist", description = "카테고리 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> storeCategoryRegist(@RequestBody StoreCategoryRegist storeCategoryRegist) {
        storeCategoryService.storeCategoryRegist(storeCategoryRegist);
        return ResponseEntity.ok(BaseResponse.success("카테고리가 등록되었습니다."));
    }

    @Operation(summary = "category update", description = "카테고리 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> storeCategoryUpdate(@RequestBody StoreCategoryUpdate storeCategoryUpdate) {
        storeCategoryService.storeCategoryUpdate(storeCategoryUpdate);
        return ResponseEntity.ok(BaseResponse.success("카테고리가 수정되었습니다."));
    }

    @Operation(summary = "category delete", description = "카테고리 삭제")
    @DeleteMapping("/delete/{categoryUUID}")
    public ResponseEntity<BaseResponse<Void>> storeCategoryDelete(@PathVariable UUID categoryUUID) {
        storeCategoryService.storeCategoryDelete(categoryUUID);
        return ResponseEntity.ok(BaseResponse.success("카테고리가 삭제되었습니다."));
    }

    @Operation(summary = "category active", description = "카테고리 활성화 / 비활성화")
    @PutMapping("/active")
    public ResponseEntity<BaseResponse<Void>> storeCategoryActive(@RequestBody StoreCategoryActive storeCategoryActive) {
        storeCategoryService.storeCategoryActive(storeCategoryActive);
        return ResponseEntity.ok(BaseResponse.success("카테고리 활성화 / 비활성화 여부가 수정되었습니다."));
    }

    @Operation(summary = "category list page", description = "카테고리 리스트 (페이지)")
    @GetMapping("/list/page")
    public ResponseEntity<BaseResponse<Page<StoreCategoryList>>> storeCategoryListPage(
            @RequestParam(required = false) String categoryName,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        var result = storeCategoryService.storeCategoryListPage(categoryName, offset, limit);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "category list all", description = "카테고리 리스트 (전체)")
    @GetMapping("/list/all")
    public ResponseEntity<BaseResponse<List<StoreCategoryListAll>>> storeCategoryListAll() {
        var result = storeCategoryService.storeCategoryListAll();
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "category info", description = "카테고리 정보")
    @GetMapping("/info/{categoryUUID}")
    public ResponseEntity<BaseResponse<StoreCategoryInfo>> storeCategoryInfo(@PathVariable UUID categoryUUID) {
        var result = storeCategoryService.storeCategoryInfo(categoryUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "카테고리 정보가 조회되었습니다."));
    }
}
