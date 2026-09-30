package com.project.dine.reserve.domain.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.auth.member.GuestLogin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_guest", indexes = {
        @Index(name = "idx_guest_uuid", columnList = "guest_uuid"),
        @Index(name = "idx_guest_phone", columnList = "guest_phone"),
        @Index(name = "idx_guest_name", columnList = "guest_name")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveGuest extends DineReserveBase {
    @Comment("비회원 UUID")
    @Column(name = "guest_uuid", length = 50, nullable = false, unique = true)
    private UUID guestUUID;

    @Comment("비회원 전화번호")
    @Column(name = "guest_phone", length = 50, nullable = false)
    private String guestPhone;

    @Comment("비회원 비밀번호")
    @Column(name = "guest_password", length = 4, nullable = false)
    private String guestPassword;

    @Comment("비회원 이름")
    @Column(name = "guest_name", length = 50, nullable = false)
    private String guestName;

    @Comment("마지막 접근날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "last_date", columnDefinition = "DATETIME")
    private LocalDateTime lastDate;

    public static DineReserveGuest create(GuestLogin guestLogin) {
        DineReserveGuest dineReserveGuest = new DineReserveGuest();
        dineReserveGuest.setGuestUUID(UUID.randomUUID());
        dineReserveGuest.setGuestPhone(guestLogin.getGuestPhone());
        dineReserveGuest.setGuestPassword(guestLogin.getGuestPassword());
        dineReserveGuest.setGuestName(guestLogin.getGuestName());
        dineReserveGuest.setLastDate(LocalDateTime.now());

        dineReserveGuest.setUseFlag(true);
        dineReserveGuest.setInsertDate(LocalDateTime.now());
        dineReserveGuest.setUpdateDate(LocalDateTime.now());

        return dineReserveGuest;
    }

    public void updateLastDate() {
        this.lastDate = LocalDateTime.now();
    }
}
