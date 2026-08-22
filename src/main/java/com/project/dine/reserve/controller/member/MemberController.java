package com.project.dine.reserve.controller.member;

import com.project.dine.reserve.dto.common.BaseResponse;
import com.project.dine.reserve.dto.member.*;
import com.project.dine.reserve.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dine/reserve/member")
@Tag(name = "사용자 관리 컨트롤러", description = "사용자 관리 API Controller 입니다.")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "member signup", description = "사용자 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> memberSignup(@RequestBody MemberSignup memberSignup) {
        memberService.memberSignup(memberSignup);
        return ResponseEntity.ok(BaseResponse.success("사용자 회원가입 되었습니다."));
    }

    @Operation(summary = "member update", description = "사용자 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<Void>> memberUpdate(@RequestBody MemberUpdate memberUpdate) {
        memberService.memberUpdate(memberUpdate);
        return ResponseEntity.ok(BaseResponse.success("사용자 정보가 수정되었습니다."));
    }

    @Operation(summary = "member delete", description = "사용자 삭제")
    @DeleteMapping("/delete/{memberUUID}")
    public ResponseEntity<BaseResponse<Void>> memberDelete(@PathVariable UUID memberUUID) {
        memberService.memberDelete(memberUUID);
        return ResponseEntity.ok(BaseResponse.success("사용자 정보가 삭제되었습니다."));
    }

    @Operation(summary = "member update password", description = "사용자 비밀번호 수정")
    @PutMapping("/update/password")
    public ResponseEntity<BaseResponse<Void>> memberUpdatePassword(@RequestBody MemberUpdatePassword memberUpdatePassword) {
        memberService.memberUpdatePassword(memberUpdatePassword);
        return ResponseEntity.ok(BaseResponse.success("사용자 비밀번호가 수정되었습니다."));
    }

    @Operation(summary = "member update status", description = "사용자 상태 수정")
    @PutMapping("/update/status")
    public ResponseEntity<BaseResponse<Void>> memberUpdateStatus(@RequestBody MemberUpdateStatus memberUpdateStatus) {
        memberService.memberUpdateStatus(memberUpdateStatus);
        return ResponseEntity.ok(BaseResponse.success("사용자 상태가 수정되었습니다."));
    }

    @Operation(summary = "member list page", description = "사용자 리스트 (페이지)")
    @GetMapping("/list/page")
    public ResponseEntity<BaseResponse<Page<MemberList>>> memberListPage(
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        var result = memberService.memberListPage(searchType, searchValue, offset, limit);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "member info", description = "사용자 정보")
    @GetMapping("/info/{memberUUID}")
    public ResponseEntity<BaseResponse<MemberInfo>> memberInfo(@PathVariable UUID memberUUID) {
        var result = memberService.memberInfo(memberUUID);
        return ResponseEntity.ok(BaseResponse.success(result, "사용자 정보가 조회되었습니다."));
    }
}
