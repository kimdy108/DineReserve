package com.project.dine.reserve.domain.store;

import com.project.dine.reserve.domain.common.DineReserveBase;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailRegist;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "dine_reserve_store_info_detail", indexes = {
    @Index(name = "idx_store_seq", columnList = "store_seq"),
    @Index(name = "idx_store_uuid", columnList = "store_uuid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
public class DineReserveStoreInfoDetail extends DineReserveBase {
    @Comment("매장 SEQ")
    @Column(name = "store_seq", length = 20, nullable = false, unique = true)
    private Long storeSeq;

    @Comment("매장 UUID")
    @Column(name = "store_uuid", length = 50, nullable = false, unique = true)
    private UUID storeUUID;

    @Comment("예약 정원")
    @Column(name = "reserve_max_person", length = 10, nullable = false)
    private int reserveMaxPerson;

    @Comment("예약 가능 시작 일")
    @Column(name = "reserve_start_day", length = 10, nullable = false)
    private int reserveStartDay;

    @Comment("예약 가능 시작 시간")
    @Column(name = "reserve_start_time", columnDefinition = "TIME")
    private LocalTime reserveStartTime;

    @Comment("예약 가능 종료 일")
    @Column(name = "reserve_end_day", length = 10, nullable = false)
    private int reserveEndDay;

    @Comment("예약 시간 단위")
    @Column(name = "reserve_time_unit", length = 10, nullable = false)
    private int reserveTimeUnit;

    @Comment("취소 가능 시간")
    @Column(name = "reserve_cancel_time", length = 10, nullable = false)
    private int rserveCancelTime;

    @Comment("이용 약관")
    @Column(name = "reserve_policy", columnDefinition = "TEXT")
    private String reservePolicy;

    @Comment("안내 사항")
    @Column(name = "reserve_information", columnDefinition = "TEXT")
    private String reserveInformation;

    public static DineReserveStoreInfoDetail create(StoreInfoDetailRegist storeInfoDetailRegist, DineReserveStoreInfo dineReserveStoreInfo) {
        DineReserveStoreInfoDetail dineReserveStoreInfoDetail = new DineReserveStoreInfoDetail();
        dineReserveStoreInfoDetail.setStoreSeq(dineReserveStoreInfo.getSeq());
        dineReserveStoreInfoDetail.setStoreUUID(dineReserveStoreInfo.getStoreUUID());
        dineReserveStoreInfoDetail.setReserveMaxPerson(storeInfoDetailRegist.getReserveMaxPerson());
        dineReserveStoreInfoDetail.setReserveStartDay(storeInfoDetailRegist.getReserveStartDay());
        dineReserveStoreInfoDetail.setReserveStartTime(storeInfoDetailRegist.getReserveStartTime());
        dineReserveStoreInfoDetail.setReserveEndDay(storeInfoDetailRegist.getReserveEndDay());
        dineReserveStoreInfoDetail.setReserveTimeUnit(storeInfoDetailRegist.getReserveTimeUnit());
        dineReserveStoreInfoDetail.setRserveCancelTime(storeInfoDetailRegist.getRserveCancelTime());
        dineReserveStoreInfoDetail.setReservePolicy(storeInfoDetailRegist.getReservePolicy());
        dineReserveStoreInfoDetail.setReserveInformation(storeInfoDetailRegist.getReserveInformation());

        dineReserveStoreInfoDetail.setUseFlag(true);
        dineReserveStoreInfoDetail.setInsertDate(LocalDateTime.now());
        dineReserveStoreInfoDetail.setUpdateDate(LocalDateTime.now());

        return dineReserveStoreInfoDetail;
    }

    public void update(StoreInfoDetailUpdate storeInfoDetailUpdate) {
        this.reserveMaxPerson = storeInfoDetailUpdate.getReserveMaxPerson();
        this.reserveStartDay = storeInfoDetailUpdate.getReserveStartDay();
        this.reserveStartTime = storeInfoDetailUpdate.getReserveStartTime();
        this.reserveEndDay = storeInfoDetailUpdate.getReserveEndDay();
        this.reserveTimeUnit = storeInfoDetailUpdate.getReserveTimeUnit();
        this.rserveCancelTime = storeInfoDetailUpdate.getRserveCancelTime();
        this.reservePolicy = storeInfoDetailUpdate.getReservePolicy();
        this.reserveInformation = storeInfoDetailUpdate.getReserveInformation();

        this.setUpdateDate(LocalDateTime.now());
    }
}
