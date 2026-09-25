package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreCategory;
import com.project.dine.reserve.domain.store.DineReserveStoreCategoryInfo;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.repository.store.DineReserveStoreCategoryInfoRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreCategoryInfoService {
    private final DineReserveStoreCategoryRepository dineReserveStoreCategoryRepository;
    private final DineReserveStoreCategoryInfoRepository dineReserveStoreCategoryInfoRepository;

    @Transactional
    public void storeCategoryInfoRegist(DineReserveStoreInfo dineReserveStoreInfo, List<UUID> categoryUUIDList) {
        List<DineReserveStoreCategoryInfo> dineReserveStoreCategoryInfos = new ArrayList<>();

        for (UUID categoryUUID : categoryUUIDList) {
            DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(categoryUUID)
                    .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));
            dineReserveStoreCategoryInfos.add(DineReserveStoreCategoryInfo.create(dineReserveStoreCategory, dineReserveStoreInfo));
        }

        dineReserveStoreCategoryInfoRepository.saveAll(dineReserveStoreCategoryInfos);
    }

    @Transactional
    public void storeCategoryInfoUpdate(DineReserveStoreInfo dineReserveStoreInfo, List<UUID> categoryUUIDList) {
        List<UUID> requestedCategoryUUIDs = new ArrayList<>(categoryUUIDList);

        List<DineReserveStoreCategoryInfo> currentDineReserveStoreCategoryInfos = dineReserveStoreCategoryInfoRepository.findAllByStoreUUID(dineReserveStoreInfo.getStoreUUID());
        List<DineReserveStoreCategoryInfo> newDineReserveStoreCategoryInfos = new ArrayList<>();

        for (DineReserveStoreCategoryInfo dineReserveStoreCategoryInfo : currentDineReserveStoreCategoryInfos) {
            if (!requestedCategoryUUIDs.remove(dineReserveStoreCategoryInfo.getCategoryUUID())) dineReserveStoreCategoryInfoRepository.delete(dineReserveStoreCategoryInfo);
        }

        for (UUID categoryUUID : requestedCategoryUUIDs) {
            DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(categoryUUID)
                    .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));
            newDineReserveStoreCategoryInfos.add(DineReserveStoreCategoryInfo.create(dineReserveStoreCategory, dineReserveStoreInfo));
        }

        if (!newDineReserveStoreCategoryInfos.isEmpty()) dineReserveStoreCategoryInfoRepository.saveAll(newDineReserveStoreCategoryInfos);
    }

    @Transactional
    public void storeCategoryInfoDelete(UUID storeUUID) {
        List<DineReserveStoreCategoryInfo> dineReserveStoreCategoryInfos = dineReserveStoreCategoryInfoRepository.findAllByStoreUUID(storeUUID);
        dineReserveStoreCategoryInfoRepository.deleteAll(dineReserveStoreCategoryInfos);
    }

    public List<UUID> storeCategoryInfoCategoryUUIDList(UUID storeUUID) {
        List<DineReserveStoreCategoryInfo> dineReserveStoreCategoryInfos = dineReserveStoreCategoryInfoRepository.findAllByStoreUUID(storeUUID);
        return dineReserveStoreCategoryInfos.stream().map(DineReserveStoreCategoryInfo::getCategoryUUID).toList();
    }
}
