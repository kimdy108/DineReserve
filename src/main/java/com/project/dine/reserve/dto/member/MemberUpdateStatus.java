package com.project.dine.reserve.dto.member;

import com.project.dine.reserve.dto.constant.member.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateStatus {
    private UUID memberUUID;
    private MemberStatusType memberStatus;
}
