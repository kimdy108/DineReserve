package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreHoliday;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayInfo;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayList;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayRegist;
import com.project.dine.reserve.dto.store.holiday.StoreHolidayUpdate;
import com.project.dine.reserve.repository.store.DineReserveStoreHolidayRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreHolidayService {
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;
    private final DineReserveStoreHolidayRepository dineReserveStoreHolidayRepository;

    @Transactional
    public void storeHolidayRegist(StoreHolidayRegist storeHolidayRegist) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeHolidayRegist.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        DineReserveStoreHoliday dineReserveStoreHoliday = DineReserveStoreHoliday.create(storeHolidayRegist, dineReserveStoreInfo);
        dineReserveStoreHolidayRepository.save(dineReserveStoreHoliday);
    }

    @Transactional
    public void storeHolidayUpdate(StoreHolidayUpdate storeHolidayUpdate) {
        DineReserveStoreHoliday dineReserveStoreHoliday = dineReserveStoreHolidayRepository.findByHolidayUUID(storeHolidayUpdate.getHolidayUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_HOLIDAY));

        dineReserveStoreHoliday.update(storeHolidayUpdate);
    }

    @Transactional
    public void storeHolidayDelete(UUID holidayUUID) {
        DineReserveStoreHoliday dineReserveStoreHoliday = dineReserveStoreHolidayRepository.findByHolidayUUID(holidayUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_HOLIDAY));

        dineReserveStoreHolidayRepository.delete(dineReserveStoreHoliday);
    }

    @Transactional
    public void storeHolidayDeleteAll(UUID storeUUID) {
        dineReserveStoreHolidayRepository.deleteAllByStoreUUID(storeUUID);
    }

    public List<StoreHolidayList> storeHolidayList(UUID storeUUID, YearMonth yearMonth) {
        return dineReserveStoreHolidayRepository.findStoreHolidayList(storeUUID, yearMonth);
    }

    public StoreHolidayInfo storeHolidayInfo(UUID holidayUUID) {
        DineReserveStoreHoliday dineReserveStoreHoliday = dineReserveStoreHolidayRepository.findByHolidayUUID(holidayUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_HOLIDAY));

        return StoreHolidayInfo.create(dineReserveStoreHoliday);
    }
}
