package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.domain.store.DineReserveStoreInfoSchedule;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.store.schedule.StoreInfoSchedule;
import com.project.dine.reserve.dto.store.schedule.StoreInfoScheduleRegist;
import com.project.dine.reserve.dto.store.schedule.StoreInfoScheduleUpdate;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreInfoScheduleService {
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;
    private final DineReserveStoreInfoScheduleRepository dineReserveStoreInfoScheduleRepository;

    @Transactional
    public void storeInfoScheduleRegist(StoreInfoScheduleRegist storeInfoScheduleRegist) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoScheduleRegist.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        for (StoreInfoSchedule storeInfoSchedule : storeInfoScheduleRegist.getStoreInfoScheduleList()) {
            DineReserveStoreInfoSchedule dineReserveStoreInfoSchedule = DineReserveStoreInfoSchedule.create(storeInfoSchedule, dineReserveStoreInfo);
            dineReserveStoreInfoScheduleRepository.save(dineReserveStoreInfoSchedule);
        }
    }

    @Transactional
    public void storeInfoScheduleUpdate(StoreInfoScheduleUpdate storeInfoScheduleUpdate) {
        List<DineReserveStoreInfoSchedule> dineReserveStoreInfoScheduleList = dineReserveStoreInfoScheduleRepository.findAllByStoreUUID(storeInfoScheduleUpdate.getStoreUUID());

        Map<DayOfWeek, DineReserveStoreInfoSchedule> dineReserveStoreInfoScheduleMap = dineReserveStoreInfoScheduleList.stream()
                .collect(Collectors.toMap(DineReserveStoreInfoSchedule::getDayOfWeek, Function.identity()));

        for (StoreInfoSchedule storeInfoSchedule : storeInfoScheduleUpdate.getStoreInfoScheduleList()) {
            DineReserveStoreInfoSchedule dineReserveStoreInfoSchedule = dineReserveStoreInfoScheduleMap.get(storeInfoSchedule.getDayOfWeek());

            dineReserveStoreInfoSchedule.update(storeInfoSchedule);
        }
    }

    @Transactional
    public void storeInfoScheduleDelete(UUID storeUUID) {
        dineReserveStoreInfoScheduleRepository.deleteByStoreUUID(storeUUID);
    }

    public List<StoreInfoSchedule> storeInfoScheduleListAll(UUID storeUUID) {
        List<StoreInfoSchedule> storeInfoScheduleList = dineReserveStoreInfoScheduleRepository.findStoreInfoScheduleListByStoreUUID(storeUUID);
        if (storeInfoScheduleList.isEmpty()) storeInfoScheduleList = getDefaultScheduleList();

        return storeInfoScheduleList;
    }

    private List<StoreInfoSchedule> getDefaultScheduleList() {
        return Arrays.stream(DayOfWeek.values())
                .map(StoreInfoSchedule::createForDefault)
                .toList();
    }
}
