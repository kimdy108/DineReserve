package com.project.dine.reserve.dto.member;

import com.project.dine.reserve.domain.member.DineReserveMember;
import com.project.dine.reserve.dto.constant.member.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
    private UUID memberUUID;
    private String memberID;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private MemberStatusType memberStatus;
    private String memberDescription;

    public static MemberInfo create(DineReserveMember dineReserveMember) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberUUID(dineReserveMember.getMemberUUID());
        memberInfo.setMemberID(dineReserveMember.getMemberID());
        memberInfo.setMemberName(dineReserveMember.getMemberName());
        memberInfo.setMemberPhone(dineReserveMember.getMemberPhone());
        memberInfo.setMemberEmail(dineReserveMember.getMemberEmail());
        memberInfo.setMemberStatus(dineReserveMember.getMemberStatus());
        memberInfo.setMemberDescription(dineReserveMember.getMemberDescription());

        return memberInfo;
    }
}
