package com.project.dine.reserve.dto.store.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoDetailUpdate {
    private UUID storeUUID;
    private int reserveMaxPerson;
    private int reserveStartDay;
    private LocalTime reserveStartTime;
    private int reserveEndDay;
    private int reserveTimeUnit;
    private int rserveCancelTime;
    private String reservePolicy;
    private String reserveInformation;
}
