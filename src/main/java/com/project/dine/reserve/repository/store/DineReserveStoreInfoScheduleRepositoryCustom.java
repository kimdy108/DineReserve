package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.dto.store.schedule.StoreInfoSchedule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DineReserveStoreInfoScheduleRepositoryCustom {
    List<StoreInfoSchedule> findStoreInfoScheduleListByStoreUUID(UUID storeUUID);
}
