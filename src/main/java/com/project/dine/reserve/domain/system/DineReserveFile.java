package com.project.dine.reserve.domain.system;

import com.project.dine.reserve.domain.common.DineReserveBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Table(name = "dine_reserve_file", indexes = {
        @Index(name = "idx_file_uuid", columnList = "file_uuid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveFile extends DineReserveBase {
    @Comment("파일 UUID")
    @Column(name = "file_uuid", length = 50, updatable = false, nullable = false, unique = true)
    private UUID fileUUID;

    @Comment("파일 원본 이름")
    @Column(name = "file_org_name", nullable = false, length = 255)
    private String fileOrgName;

    @Comment("파일 저장 이름")
    @Column(name = "file_save_name", nullable = false, length = 255)
    private String fileSaveName;

    @Comment("파일 크기")
    @Column(name = "file_size", nullable = false, length = 11)
    private Long fileSize;

    @Comment("파일 타입")
    @Column(name = "file_type", nullable = false, length = 200)
    private String fileType;

    @Comment("확장자")
    @Column(name = "file_extension", nullable = false, length = 5)
    private String fileExtension;

    @Comment("경로")
    @Column(name = "file_path", nullable = false, length = 20)
    private String filePath;

    @Comment("용도")
    @Column(name = "file_usage", nullable = false, length = 20)
    private String fileUsage;
}
