package com.project.dine.reserve.dto.store.holiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreHolidayUpdate {
    private UUID holidayUUID;
    private LocalDate holidayDate;
    private String holidayReason;
    private boolean isRepeat;
}
