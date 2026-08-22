package com.project.dine.reserve.controller.admin;

import com.project.dine.reserve.dto.auth.admin.*;
import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.service.admin.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/admin")
@Tag(name = "관리자 관리 컨트롤러", description = "관리자 관리 API Controller 입니다.")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "admin regist", description = "관리자 등록")
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Void>> adminRegist(@RequestBody AdminRegist adminRegist) {
        adminService.adminRegist(adminRegist);
        return ResponseEntity.ok(BaseResponse.success("관리자 등록되었습니다."));
    }

    @Operation(summary = "admin update", description = "관리자 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> adminUpdate(@RequestBody AdminUpdate adminUpdate) {
        adminService.adminUpdate(adminUpdate);
        return ResponseEntity.ok(BaseResponse.success("관리자 정보가 수정되었습니다."));
    }

    @Operation(summary = "admin delete", description = "관리자 삭제")
    @DeleteMapping("/delete/{adminUUID}")
    public ResponseEntity<BaseResponse<Void>> adminDelete(@PathVariable UUID adminUUID) {
        adminService.adminDelete(adminUUID);
        return ResponseEntity.ok(BaseResponse.success("관리자 정보가 삭제되었습니다."));
    }

    @Operation(summary = "admin update password", description = "관리자 비밀번호 수정")
    @PutMapping("/update/password")
    public ResponseEntity<BaseResponse<Void>> adminUpdatePassword(@RequestBody AdminUpdatePassword adminUpdatePassword) {
        adminService.adminUpdatePassword(adminUpdatePassword);
        return ResponseEntity.ok(BaseResponse.success("관리자 비밀번호가 수정되었습니다."));
    }

    @Operation(summary = "admin list page", description = "관리자 리스트(페이지)")
    @GetMapping("/list/page")
    public ResponseEntity<BaseResponse<Page<AdminList>>> adminListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        var result = adminService.adminListPage(searchType, searchValue, offset, limit);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "admin info", description = "관리자 정보")
    @GetMapping("/info/{adminUUID}")
    public ResponseEntity<BaseResponse<AdminInfo>> adminInfo(@PathVariable UUID adminUUID) {
        var result = adminService.adminInfo(adminUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "관리자 정보가 조회되었습니다."));
    }
}
