package com.project.dine.reserve.dto.store.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoScheduleRegist {
    private UUID storeUUID;
    private List<StoreInfoSchedule> storeInfoScheduleList;
}
