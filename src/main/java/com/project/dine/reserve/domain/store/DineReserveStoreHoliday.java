package com.project.dine.reserve.domain.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayRegist;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_store_holiday", indexes = {
        @Index(name = "idx_holiday_uuid", columnList = "holiday_uuid"),
        @Index(name = "idx_store_seq", columnList = "store_seq"),
        @Index(name = "idx_store_uuid", columnList = "store_uuid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveStoreHoliday extends DineReserveBase {
    @Comment("휴일 UUID")
    @Column(name = "holiday_uuid", length = 50, nullable = false, unique = true)
    private UUID holidayUUID;

    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false)
    private UUID storeUUID;

    @Comment("휴일 날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "holiday_date", columnDefinition = "DATE")
    private LocalDate holidayDate;

    @Comment("휴일 사유")
    @Column(name = "holiday_reason", length = 100, nullable = false)
    private String holidayReason;

    @Comment("반복 여부")
    @Column(name = "is_repeat", columnDefinition = "bit(1) default false")
    private boolean isRepeat;

    public static DineReserveStoreHoliday create(StoreHolidayRegist storeHolidayRegist, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveStoreHoliday dineReserveStoreHoliday = new DineReserveStoreHoliday();
        dineReserveStoreHoliday.setHolidayUUID(UUID.randomUUID());
        dineReserveStoreHoliday.setStoreSeq(dineReserveStoreInfo.getSeq());
        dineReserveStoreHoliday.setStoreUUID(dineReserveStoreInfo.getStoreUUID());
        dineReserveStoreHoliday.setHolidayDate(storeHolidayRegist.getHolidayDate());
        dineReserveStoreHoliday.setHolidayReason(storeHolidayRegist.getHolidayReason());
        dineReserveStoreHoliday.setRepeat(storeHolidayRegist.isRepeat());

        dineReserveStoreHoliday.setUseFlag(true);
        dineReserveStoreHoliday.setInsertDate(LocalDateTime.now());
        dineReserveStoreHoliday.setUpdateDate(LocalDateTime.now());

        return dineReserveStoreHoliday;
    }

    public void update(StoreHolidayUpdate storeHolidayUpdate) {
        this.holidayDate = storeHolidayUpdate.getHolidayDate();
        this.holidayReason = storeHolidayUpdate.getHolidayReason();
        this.isRepeat = storeHolidayUpdate.isRepeat();

        this.setUpdateDate(LocalDateTime.now());
    }
}
