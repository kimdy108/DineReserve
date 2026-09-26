package com.project.dine.reserve.dto.store.detail;

import com.project.dine.reserve.domain.store.DineReserveStoreInfoDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoDetailInfo {
    private int reserveMaxPerson;
    private int reserveStartDay;
    private LocalTime reserveStartTime;
    private int reserveEndDay;
    private int reserveTimeUnit;
    private int rserveCancelTime;
    private String reservePolicy;
    private String reserveInformation;

    public static StoreInfoDetailInfo create(DineReserveStoreInfoDetail dineReserveStoreInfoDetail) {
        StoreInfoDetailInfo storeInfoDetailInfo = new StoreInfoDetailInfo();
        storeInfoDetailInfo.setReserveMaxPerson(dineReserveStoreInfoDetail.getReserveMaxPerson());
        storeInfoDetailInfo.setReserveStartDay(dineReserveStoreInfoDetail.getReserveStartDay());
        storeInfoDetailInfo.setReserveStartTime(dineReserveStoreInfoDetail.getReserveStartTime());
        storeInfoDetailInfo.setReserveEndDay(dineReserveStoreInfoDetail.getReserveEndDay());
        storeInfoDetailInfo.setReserveTimeUnit(dineReserveStoreInfoDetail.getReserveTimeUnit());
        storeInfoDetailInfo.setRserveCancelTime(dineReserveStoreInfoDetail.getRserveCancelTime());
        storeInfoDetailInfo.setReservePolicy(dineReserveStoreInfoDetail.getReservePolicy());
        storeInfoDetailInfo.setReserveInformation(dineReserveStoreInfoDetail.getReserveInformation());

        return storeInfoDetailInfo;
    }

    public static StoreInfoDetailInfo defaultCreate() {
        StoreInfoDetailInfo storeInfoDetailInfo = new StoreInfoDetailInfo();
        storeInfoDetailInfo.setReserveMaxPerson(0);
        storeInfoDetailInfo.setReserveStartDay(0);
        storeInfoDetailInfo.setReserveStartTime(LocalTime.parse("00:00"));
        storeInfoDetailInfo.setReserveEndDay(1);
        storeInfoDetailInfo.setReserveTimeUnit(60);
        storeInfoDetailInfo.setRserveCancelTime(0);
        storeInfoDetailInfo.setReservePolicy("");
        storeInfoDetailInfo.setReserveInformation("");

        return storeInfoDetailInfo;
    }
}
