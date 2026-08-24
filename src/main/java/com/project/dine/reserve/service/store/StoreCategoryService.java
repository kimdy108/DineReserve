package com.project.dine.reserve.service.store;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.store.DineReserveStoreCategory;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.store.category.*;
import com.project.dine.reserve.repository.store.DineReserveStoreCategoryRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
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
public class StoreCategoryService {
    private final DineReserveStoreCategoryRepository dineReserveStoreCategoryRepository;
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;

    @Transactional
    public void storeCategoryRegist(StoreCategoryRegist storeCategoryRegist) {
        dineReserveStoreCategoryRepository.findByCategoryName(storeCategoryRegist.getCategoryName()).ifPresent(x -> {
            throw new DineReserveException(StoreErrorCode.EXIST_STORE_CATEGORY);
        });

        DineReserveStoreCategory dineReserveStoreCategory = DineReserveStoreCategory.create(storeCategoryRegist);
        dineReserveStoreCategoryRepository.save(dineReserveStoreCategory);
    }

    @Transactional
    public void storeCategoryUpdate(StoreCategoryUpdate storeCategoryUpdate) {
        DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(storeCategoryUpdate.getCategoryUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));

        dineReserveStoreCategory.update(storeCategoryUpdate);
    }

    @Transactional
    public void storeCategoryDelete(UUID categoryUUID) {
        DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(categoryUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));

        int count = dineReserveStoreInfoRepository.countByCategoryUUID(dineReserveStoreCategory.getCategoryUUID());
        if (count > 0) throw new DineReserveException(StoreErrorCode.USE_CATEGORY);

        dineReserveStoreCategoryRepository.delete(dineReserveStoreCategory);
    }

    @Transactional
    public void storeCategoryActive(StoreCategoryActive storeCategoryActive) {
        DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(storeCategoryActive.getCategoryUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));

        dineReserveStoreCategory.updateUseFlag(storeCategoryActive.isUseFlag());
    }

    public Page<StoreCategoryList> storeCategoryListPage(String categoryName, Long offset, int limit) {
        if (categoryName == null) categoryName = "";

        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return dineReserveStoreCategoryRepository.findStoreCategoryListPage(categoryName, offset, limit, pageable);
    }

    public List<StoreCategoryListAll> storeCategoryListAll() {
        return dineReserveStoreCategoryRepository.findStoreCategoryListAll();
    }

    public StoreCategoryInfo storeCategoryInfo(UUID categoryUUID) {
        DineReserveStoreCategory dineReserveStoreCategory = dineReserveStoreCategoryRepository.findByCategoryUUID(categoryUUID)
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_CATEGORY));

        return StoreCategoryInfo.create(dineReserveStoreCategory);
    }
}
