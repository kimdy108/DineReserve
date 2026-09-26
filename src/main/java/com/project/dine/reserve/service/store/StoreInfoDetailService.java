package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.domain.store.DineReserveStoreInfoDetail;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailInfo;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailRegist;
import com.project.dine.reserve.dto.store.detail.StoreInfoDetailUpdate;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoDetailRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreInfoDetailService {
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;
    private final DineReserveStoreInfoDetailRepository dineReserveStoreInfoDetailRepository;

    @Transactional
    public void storeInfoDetailRegist(StoreInfoDetailRegist storeInfoDetailRegist) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoDetailRegist.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        DineReserveStoreInfoDetail dineReserveStoreInfoDetail = DineReserveStoreInfoDetail.create(storeInfoDetailRegist, dineReserveStoreInfo);
        dineReserveStoreInfoDetailRepository.save(dineReserveStoreInfoDetail);
    }

    @Transactional
    public void storeInfoDetailUpdate(StoreInfoDetailUpdate storeInfoDetailUpdate) {
        DineReserveStoreInfoDetail dineReserveStoreInfoDetail = dineReserveStoreInfoDetailRepository.findByStoreUUID(storeInfoDetailUpdate.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO_DETAIL));

        dineReserveStoreInfoDetail.update(storeInfoDetailUpdate);
    }

    @Transactional
    public void storeInfoDetailDelete(UUID storeUUID) {
        DineReserveStoreInfoDetail dineReserveStoreInfoDetail = dineReserveStoreInfoDetailRepository.findByStoreUUID(storeUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO_DETAIL));

        dineReserveStoreInfoDetailRepository.delete(dineReserveStoreInfoDetail);
    }

    public StoreInfoDetailInfo storeInfoDetailInfo(UUID storeUUID) {
        DineReserveStoreInfoDetail dineReserveStoreInfoDetail = dineReserveStoreInfoDetailRepository.findByStoreUUID(storeUUID)
                .orElse(null);

        return dineReserveStoreInfoDetail == null ? StoreInfoDetailInfo.defaultCreate() : StoreInfoDetailInfo.create(dineReserveStoreInfoDetail);
    }
}
