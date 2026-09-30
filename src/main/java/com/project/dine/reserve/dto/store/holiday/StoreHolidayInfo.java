package com.project.dine.reserve.dto.store.holiday;

import com.project.dine.reserve.domain.store.DineReserveStoreHoliday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreHolidayInfo {
    private UUID holidayUUID;
    private LocalDate holidayDate;
    private String holidayReason;
    private boolean isRepeat;
    private LocalDateTime insertDate;

    public static StoreHolidayInfo create(DineReserveStoreHoliday dineReserveStoreHoliday) {
        StoreHolidayInfo storeHolidayInfo = new StoreHolidayInfo();
        storeHolidayInfo.setHolidayUUID(dineReserveStoreHoliday.getHolidayUUID());
        storeHolidayInfo.setHolidayDate(dineReserveStoreHoliday.getHolidayDate());
        storeHolidayInfo.setHolidayReason(dineReserveStoreHoliday.getHolidayReason());
        storeHolidayInfo.setRepeat(dineReserveStoreHoliday.isRepeat());
        storeHolidayInfo.setInsertDate(dineReserveStoreHoliday.getInsertDate());

        return storeHolidayInfo;
    }
}
