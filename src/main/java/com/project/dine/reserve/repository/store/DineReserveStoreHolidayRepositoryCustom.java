package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.dto.store.holiday.StoreHolidayList;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Repository
public interface DineReserveStoreHolidayRepositoryCustom {
    List<StoreHolidayList> findStoreHolidayList(UUID storeUUID, YearMonth yearMonth);
}
