package com.project.dine.reserve.domain.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.constant.admin.AdminRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_admin", indexes = {
        @Index(name = "idx_admin_uuid", columnList = "admin_uuid"),
        @Index(name = "idx_admin_id", columnList = "admin_id"),
        @Index(name = "idx_admin_name", columnList = "admin_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DineReserveAdmin extends DineReserveBase {
    @Comment("관리자 UUID")
    @Column(name = "admin_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID adminUUID;

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
}
