package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.domain.system.DineReserveFile;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.store.info.*;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class StoreInfoService {
    private final FileService fileService;
    private final StoreInfoDetailService storeInfoDetailService;
    private final StoreInfoScheduleService storeInfoScheduleService;
    private final StoreCategoryInfoService storeCategoryInfoService;

    private final DineReserveFileRepository dineReserveFileRepository;

    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;

    @Transactional
    public void storeInfoRegist(StoreInfoRegist storeInfoRegist) {
        dineReserveStoreInfoRepository.findByStoreName(storeInfoRegist.getStoreName()).ifPresent(s -> {
            throw new DineReserveException(StoreErrorCode.EXIST_STORE_INFO);
        });

        DineReserveFile storeImg = fileService.insertFile(storeInfoRegist.getStoreImg(), "store", "img");
        DineReserveFile storeMap = fileService.insertFile(storeInfoRegist.getStoreMap(), "store", "map");

        DineReserveStoreInfo dineReserveStoreInfo = DineReserveStoreInfo.create(storeInfoRegist, storeImg, storeMap);
        dineReserveStoreInfoRepository.save(dineReserveStoreInfo);

        // 매장 카테고리, 정보 매핑 등록
        storeCategoryInfoService.storeCategoryInfoRegist(dineReserveStoreInfo, storeInfoRegist.getCategoryUUIDList());
    }

    @Transactional
    public void storeInfoUpdate(StoreInfoUpdate storeInfoUpdate) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeInfoUpdate.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        DineReserveFile storeImg = storeInfoUpdate.getStoreImg() == null ?
                dineReserveFileRepository.findByFileUUID(dineReserveStoreInfo.getStoreImgUUID()).orElse(null) :
                fileService.updateFile(dineReserveStoreInfo.getStoreImgUUID(), storeInfoUpdate.getStoreImg(), "store", "img");
        DineReserveFile storeMap = storeInfoUpdate.getStoreMap() == null ?
                dineReserveFileRepository.findByFileUUID(dineReserveStoreInfo.getStoreMapUUID()).orElse(null) :
                fileService.updateFile(dineReserveStoreInfo.getStoreMapUUID(), storeInfoUpdate.getStoreMap(), "store", "map");

        dineReserveStoreInfo.update(storeInfoUpdate, storeImg, storeMap);

        // 매장 카테고리, 정보 매핑 수정
        storeCategoryInfoService.storeCategoryInfoUpdate(dineReserveStoreInfo, storeInfoUpdate.getCategoryUUIDList());
    }

    @Transactional
    public void storeInfoDelete(UUID storeUUID) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        fileService.deleteFile(dineReserveStoreInfo.getStoreImgUUID());
        fileService.deleteFile(dineReserveStoreInfo.getStoreMapUUID());

        dineReserveStoreInfoRepository.delete(dineReserveStoreInfo);

        // 매장 상세 삭제
        storeInfoDetailService.storeInfoDetailDelete(storeUUID);

        // 매장 카테고리, 정보 매핑 삭제
        storeCategoryInfoService.storeCategoryInfoDelete(storeUUID);

        // 매장 스케줄 삭제
        storeInfoScheduleService.storeInfoScheduleDelete(storeUUID);
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

    public StoreInfoInfo storeInfoInfo(UUID storeUUID) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(storeUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        return StoreInfoInfo.create(dineReserveStoreInfo, storeCategoryInfoService.storeCategoryInfoCategoryUUIDList(dineReserveStoreInfo.getStoreUUID()));
    }
}
