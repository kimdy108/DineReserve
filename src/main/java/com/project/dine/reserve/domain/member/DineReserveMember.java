package com.project.dine.reserve.domain.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.constant.member.MemberStatusType;
import com.project.dine.reserve.dto.member.MemberSignup;
import com.project.dine.reserve.dto.member.MemberUpdate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_member", indexes = {
        @Index(name ="idx_member_uuid", columnList = "member_uuid"),
        @Index(name ="idx_member_id", columnList = "member_id"),
        @Index(name ="idx_member_name", columnList = "member_name")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveMember extends DineReserveBase {
    @Comment("사용자 UUID")
    @Column(name = "member_uuid", length = 50, nullable = false, unique = true)
    private UUID memberUUID;

    @Comment("사용자 ID")
    @Column(name = "member_id", length = 20, nullable = false, unique = true)
    private String memberID;

    @Comment("사용자 비밀번호")
    @Column(name = "member_password", length = 200, nullable = false, unique = true)
    private String memberPassword;

    @Comment("사용자 이름")
    @Column(name = "member_name", length = 50, nullable = false)
    private String memberName;

    @Comment("사용자 전화번호")
    @Column(name = "member_phone", length = 50, nullable = false)
    private String memberPhone;

    @Comment("사용자 Email")
    @Column(name = "member_email", length = 200, nullable = false)
    private String memberEmail;

    @Comment("사용자 상태")
    @Column(name = "member_status", columnDefinition = "ENUM('ACTIVE','SUSPENDED') NOT NULL DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private MemberStatusType memberStatus;

    @Comment("마지막 접근날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "last_date", columnDefinition = "DATETIME")
    private LocalDateTime lastDate;

    @Comment("비고")
    @Column(name = "member_description", columnDefinition = "TEXT")
    private String memberDescription;

    public static DineReserveMember create(MemberSignup memberSignup, String memberPassword) {
        DineReserveMember dineReserveMember = new DineReserveMember();
        dineReserveMember.setMemberUUID(UUID.randomUUID());
        dineReserveMember.setMemberID(memberSignup.getMemberID());
        dineReserveMember.setMemberPassword(memberPassword);
        dineReserveMember.setMemberName(memberSignup.getMemberName());
        dineReserveMember.setMemberPhone(memberSignup.getMemberPhone());
        dineReserveMember.setMemberEmail(memberSignup.getMemberEmail());
        dineReserveMember.setMemberStatus(MemberStatusType.ACTIVE);
        dineReserveMember.setLastDate(LocalDateTime.parse("2000-01-01T00:00:00"));

        dineReserveMember.setUseFlag(true);
        dineReserveMember.setInsertDate(LocalDateTime.now());
        dineReserveMember.setUpdateDate(LocalDateTime.now());

        return dineReserveMember;
    }

    public void update(MemberUpdate memberUpdate) {
        this.memberName = memberUpdate.getMemberName();
        this.memberPhone = memberUpdate.getMemberPhone();
        this.memberEmail = memberUpdate.getMemberEmail();
        this.memberDescription = memberUpdate.getMemberDescription();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updatePassword(String memberPassword) {
        this.memberPassword = memberPassword;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(MemberStatusType memberStatus) {
        this.memberStatus = memberStatus;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateUseFlag(boolean useFlag) {
        this.setUseFlag(useFlag);
        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateLastDate() {
        this.lastDate = LocalDateTime.now();
    }
}
