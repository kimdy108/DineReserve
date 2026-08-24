package com.project.dine.reserve.domain.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.admin.AdminRegist;
import com.project.dine.reserve.dto.admin.AdminUpdate;
import com.project.dine.reserve.dto.constant.admin.AdminRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.dine.reserve.util.Common.EMPTY_SEQ;
import static com.project.dine.reserve.util.Common.EMPTY_UUID;

@Entity
@Table(name = "dine_reserve_admin", indexes = {
        @Index(name = "idx_admin_uuid", columnList = "admin_uuid"),
        @Index(name = "idx_store_seq", columnList = "store_seq"),
        @Index(name = "idx_store_uuid", columnList = "store_uuid"),
        @Index(name = "idx_admin_id", columnList = "admin_id"),
        @Index(name = "idx_admin_name", columnList = "admin_name")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveAdmin extends DineReserveBase {
    @Comment("관리자 UUID")
    @Column(name = "admin_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID adminUUID;

    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false)
    private UUID storeUUID;

    @Comment("관리자 ID")
    @Column(name = "admin_id", length = 20, unique = true, nullable = false, updatable = false)
    private String adminID;

    @Comment("관리자 비밀번호")
    @Column(name = "admin_password", length = 200, nullable = false)
    private String adminPassword;

    @Comment("관리자 이름")
    @Column(name = "admin_name", length = 50, nullable = false)
    private String adminName;

    @Comment("관리자 전화번호")
    @Column(name = "admin_phone", length = 50, nullable = false)
    private String adminPhone;

    @Comment("관리자 Email")
    @Column(name = "admin_email", length = 50, nullable = false)
    private String adminEmail;

    @Comment("관리자 권한")
    @Column(name = "admin_role", columnDefinition = "ENUM('ADMIN','STORE_ADMIN','STORE_MANAGER') NOT NULL DEFAULT 'STORE_MANAGER'")
    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;

    @Comment("마지막 로그인 날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "last_date", columnDefinition = "DATETIME")
    private LocalDateTime lastDate;

    @Comment("비고")
    @Column(name = "admin_description", columnDefinition = "TEXT")
    private String adminDescription;

    public static DineReserveAdmin create(AdminRegist adminRegist, String adminPassword, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveAdmin dineReserveAdmin = new DineReserveAdmin();
        dineReserveAdmin.setAdminUUID(UUID.randomUUID());
        dineReserveAdmin.setStoreSeq(dineReserveStoreInfo == null ? EMPTY_SEQ : dineReserveStoreInfo.getSeq());
        dineReserveAdmin.setStoreUUID(dineReserveStoreInfo == null ? EMPTY_UUID : dineReserveStoreInfo.getStoreUUID());
        dineReserveAdmin.setAdminID(adminRegist.getAdminID());
        dineReserveAdmin.setAdminPassword(adminPassword);
        dineReserveAdmin.setAdminName(adminRegist.getAdminName());
        dineReserveAdmin.setAdminPhone(adminRegist.getAdminPhone());
        dineReserveAdmin.setAdminEmail(adminRegist.getAdminEmail());
        dineReserveAdmin.setAdminRole(adminRegist.getAdminRole());
        dineReserveAdmin.setLastDate(LocalDateTime.parse("2000-01-29T00:00:00"));
        dineReserveAdmin.setAdminDescription(adminRegist.getAdminDescription());

        dineReserveAdmin.setUseFlag(true);
        dineReserveAdmin.setInsertDate(LocalDateTime.now());
        dineReserveAdmin.setUpdateDate(LocalDateTime.now());

        return dineReserveAdmin;
    }

    public void update(AdminUpdate adminUpdate) {
        this.adminName = adminUpdate.getAdminName();
        this.adminPhone = adminUpdate.getAdminPhone();
        this.adminEmail = adminUpdate.getAdminEmail();
        this.adminRole = adminUpdate.getAdminRole();

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updatePassword(String adminPassword) {
        this.adminPassword = adminPassword;

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
