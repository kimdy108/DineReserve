package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreCategory;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.domain.system.DineReserveFile;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.constant.error.SystemErrorCode;
import com.project.dine.reserve.dto.store.info.*;
import com.project.dine.reserve.repository.store.DineReserveStoreCategoryRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
import com.project.dine.reserve.repository.system.DineReserveFileRepository;
import com.project.dine.reserve.service.component.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreInfoService {
    private final FileService fileService;

    private final DineReserveFileRepository dineReserveFileRepository;

    private final DineReserveStoreCategoryRepository dineReserveStoreCategoryRepository;
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;

    @Transactional
    public void storeInfoRegist(StoreInfoRegist storeInfoRegist) {
        dineReserveStoreInfoRepository.findByStoreName(storeInfoRegist.getStoreName()).ifPresent(s -> {
            throw new DineReserveException(StoreErrorCode.EXIST_STORE_INFO);
        });

        DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(storeInfoRegist.getCategoryUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));

        DineReserveFile storeImg = fileService.insertFile(storeInfoRegist.getStoreImg(), "store", "img");
        DineReserveFile storeMap = fileService.insertFile(storeInfoRegist.getStoreMap(), "store", "map");

        DineReserveStoreInfo dineReserveStoreInfo = DineReserveStoreInfo.create(storeInfoRegist, dineReserveStoreCategory, storeImg, storeMap);
        dineReserveStoreInfoRepository.save(dineReserveStoreInfo);
    }

    @Transactional
    public void storeInfoUpdate(StoreInfoUpdate storeInfoUpdate) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoUpdate.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        DineReserveFile storeImg = storeInfoUpdate.getStoreImg() == null ? dineReserveFileRepository.findByFileUUID(dineReserveStoreInfo.getStoreImgUUID())
                .orElseThrow(() -> new DineReserveException(SystemErrorCode.NO_FILE)) :
                fileService.updateFile(dineReserveStoreInfo.getStoreImgUUID(), storeInfoUpdate.getStoreImg(), "store", "img");
        DineReserveFile storeMap = storeInfoUpdate.getStoreMap() == null ? dineReserveFileRepository.findByFileUUID(dineReserveStoreInfo.getStoreMapUUID())
                .orElseThrow(() -> new DineReserveException(SystemErrorCode.NO_FILE)) :
                fileService.updateFile(dineReserveStoreInfo.getStoreMapUUID(), storeInfoUpdate.getStoreMap(), "store", "map");

        dineReserveStoreInfo.update(storeInfoUpdate, storeImg, storeMap);
    }

    @Transactional
    public void storeInfoDelete(UUID storeInfoUUID) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        dineReserveStoreInfoRepository.delete(dineReserveStoreInfo);
    }

    @Transactional
    public void storeInfoActive(StoreInfoActive storeInfoActive) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoActive.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        dineReserveStoreInfo.updateUseFlag(storeInfoActive.isUseFlag());
    }

    public Page<StoreInfoList> storeInfoListPage(String searchType, String searchValue, String categoryUUID, String storeStatus, Long offset, int limit) {
        if (searchType == null) searchType = "";
        if (searchValue == null) searchValue = "";
        if (categoryUUID == null) categoryUUID = "";
        if (storeStatus == null) storeStatus = "";

        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return dineReserveStoreInfoRepository.findStoreInfoListPage(searchType, searchValue, categoryUUID, storeStatus, offset, limit, pageable);
    }

    public List<StoreInfoListAll> storeInfoListAll() {
        return dineReserveStoreInfoRepository.findStoreInfoListAll();
    }

    public StoreInfoInfo storeInfoInfo(UUID storeInfoUUID) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(dineReserveStoreInfo.getCategoryUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));

        return StoreInfoInfo.create(dineReserveStoreInfo, dineReserveStoreCategory);
    }
}
