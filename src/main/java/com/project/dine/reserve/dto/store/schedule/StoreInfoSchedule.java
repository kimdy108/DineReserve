package com.project.dine.reserve.dto.store.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoSchedule {
    private DayOfWeek dayOfWeek;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private boolean storeOff;
    private boolean useBreakTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    public static StoreInfoSchedule createForDefault(DayOfWeek dayOfWeek) {
        StoreInfoSchedule storeInfoSchedule = new StoreInfoSchedule();

        storeInfoSchedule.setDayOfWeek(dayOfWeek);
        storeInfoSchedule.setWorkStartTime(LocalTime.of(0, 0, 0, 0));
        storeInfoSchedule.setWorkEndTime(LocalTime.of(0, 0, 0, 0));
        storeInfoSchedule.setStoreOff(true);
        storeInfoSchedule.setUseBreakTime(false);
        storeInfoSchedule.setBreakStartTime(LocalTime.of(0, 0, 0, 0));
        storeInfoSchedule.setBreakEndTime(LocalTime.of(0, 0, 0, 0));

        return storeInfoSchedule;
    }
}
