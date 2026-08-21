package com.project.dine.reserve.dto.member;

import com.project.dine.reserve.dto.constant.member.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberList {
    private UUID memberUUID;
    private String memberID;
    private String memberName;
    private String memberEmail;
    private MemberStatusType memberStatus;
}
