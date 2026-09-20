package com.project.dine.reserve.domain.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.store.schedule.StoreInfoSchedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_store_info_schedule", indexes = {
        @Index(name = "idx_store_seq", columnList = "store_seq"),
        @Index(name = "idx_store_uuid", columnList = "store_uuid"),
        @Index(name = "idx_day_of_week", columnList = "day_of_week")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveStoreInfoSchedule extends DineReserveBase {
    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false)
    private UUID storeUUID;

    @Comment("요일")
    @Column(name = "day_of_week", columnDefinition = "ENUM('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY') NOT NULL DEFAULT 'SUNDAY'")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Comment("업무 시작 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    @Column(name = "work_start_time", columnDefinition = "TIME")
    private LocalTime workStartTime;

    @Comment("업무 종료 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    @Column(name = "work_end_time", columnDefinition = "TIME")
    private LocalTime workEndTime;

    @Comment("정기 휴무 여부")
    @Column(name = "store_off", columnDefinition = "bit(1) default false")
    private boolean storeOff;

    @Comment("휴게 시간 여부")
    @Column(name = "use_break_time", columnDefinition = "bit(1) default false")
    private boolean useBreakTime;

    @Comment("휴게 시작 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    @Column(name = "break_start_time", columnDefinition = "TIME")
    private LocalTime breakStartTime;

    @Comment("휴게 종료 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    @Column(name = "break_end_time", columnDefinition = "TIME")
    private LocalTime breakEndTime;

    public static DineReserveStoreInfoSchedule create(StoreInfoSchedule storeInfoSchedule, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveStoreInfoSchedule dineReserveStoreInfoSchedule = new DineReserveStoreInfoSchedule();
        dineReserveStoreInfoSchedule.setStoreSeq(dineReserveStoreInfo.getSeq());
        dineReserveStoreInfoSchedule.setStoreUUID(dineReserveStoreInfo.getStoreUUID());
        dineReserveStoreInfoSchedule.setDayOfWeek(storeInfoSchedule.getDayOfWeek());
        dineReserveStoreInfoSchedule.setWorkStartTime(storeInfoSchedule.getWorkStartTime());
        dineReserveStoreInfoSchedule.setWorkEndTime(storeInfoSchedule.getWorkEndTime());
        dineReserveStoreInfoSchedule.setStoreOff(storeInfoSchedule.isStoreOff());
        dineReserveStoreInfoSchedule.setUseBreakTime(storeInfoSchedule.isUseBreakTime());
        dineReserveStoreInfoSchedule.setBreakStartTime(storeInfoSchedule.getBreakStartTime());
        dineReserveStoreInfoSchedule.setBreakEndTime(storeInfoSchedule.getBreakEndTime());

        dineReserveStoreInfo.setUseFlag(true);
        dineReserveStoreInfo.setInsertDate(LocalDateTime.now());
        dineReserveStoreInfo.setUpdateDate(LocalDateTime.now());

        return dineReserveStoreInfoSchedule;
    }

    public void update(StoreInfoSchedule storeInfoSchedule) {
        this.workStartTime = storeInfoSchedule.getWorkStartTime();
        this.workEndTime = storeInfoSchedule.getWorkEndTime();
        this.storeOff = storeInfoSchedule.isStoreOff();
        this.useBreakTime = storeInfoSchedule.isUseBreakTime();
        this.breakStartTime = storeInfoSchedule.getBreakStartTime();
        this.breakEndTime = storeInfoSchedule.getBreakEndTime();

        this.setUpdateDate(LocalDateTime.now());
    }
}
