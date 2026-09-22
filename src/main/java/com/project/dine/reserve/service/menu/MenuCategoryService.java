package com.project.dine.reserve.service.menu;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.menu.DineReserveMenuCategory;
import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import com.project.dine.reserve.dto.constant.error.MenuErrorCode;
import com.project.dine.reserve.dto.constant.error.StoreErrorCode;
import com.project.dine.reserve.dto.menu.category.*;
import com.project.dine.reserve.repository.menu.DineReserveMenuCategoryRepository;
import com.project.dine.reserve.repository.store.DineReserveStoreInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {
    private final DineReserveStoreInfoRepository dineReserveStoreInfoRepository;
    private final DineReserveMenuCategoryRepository dineReserveMenuCategoryRepository;

    @Transactional
    public void menuCategoryRegist(MenuCategoryRegist menuCategoryRegist) {
        DineReserveStoreInfo dineReserveStoreInfo = dineReserveStoreInfoRepository.findByStoreUUID(menuCategoryRegist.getStoreUUID())
                .orElseThrow(() -> new DineReserveException(StoreErrorCode.NO_STORE_INFO));

        dineReserveMenuCategoryRepository.findByStoreUUIDAndMenuCategoryName(dineReserveStoreInfo.getStoreUUID(), menuCategoryRegist.getMenuCategoryName()).ifPresent(x -> {
            throw new DineReserveException(MenuErrorCode.EXIST_MENU_CATEGORY);
        });

        int maxSequence = dineReserveMenuCategoryRepository.countMaxSequenceByStoreUUID(dineReserveStoreInfo.getStoreUUID());

        DineReserveMenuCategory dineReserveMenuCategory = DineReserveMenuCategory.create(menuCategoryRegist, maxSequence, dineReserveStoreInfo);
        dineReserveMenuCategoryRepository.save(dineReserveMenuCategory);
    }

    @Transactional
    public void menuCategoryUpdate(MenuCategoryUpdate menuCategoryUpdate) {
        DineReserveMenuCategory dineReserveMenuCategory = dineReserveMenuCategoryRepository.findByMenuCategoryUUID(menuCategoryUpdate.getMenuCategoryUUID())
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_CATEGORY));

        dineReserveMenuCategory.update(menuCategoryUpdate);
    }

    @Transactional
    public void menuCategoryDelete(UUID menuCategoryUUID) {
        DineReserveMenuCategory dineReserveMenuCategory = dineReserveMenuCategoryRepository.findByMenuCategoryUUID(menuCategoryUUID)
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_CATEGORY));

        // todo menu info count

        dineReserveMenuCategoryRepository.delete(dineReserveMenuCategory);
    }

    @Transactional
    public void menuCategoryActive(MenuCategoryActive menuCategoryActive) {
        DineReserveMenuCategory dineReserveMenuCategory = dineReserveMenuCategoryRepository.findByMenuCategoryUUID(menuCategoryActive.getMenuCategoryUUID())
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_CATEGORY));

        dineReserveMenuCategory.updateUseFlag(menuCategoryActive.isUseFlag());
    }

    @Transactional
    public void menuCategorySequence(MenuCategorySequenceUpdate menuCategorySequenceUpdate) {
        List<DineReserveMenuCategory> dineReserveMenuCategoryList = dineReserveMenuCategoryRepository.findAllByStoreUUID(menuCategorySequenceUpdate.getStoreUUID());
        Map<UUID, DineReserveMenuCategory> dineReserveMenuCategoryMap = dineReserveMenuCategoryList.stream()
                .collect(Collectors.toMap(DineReserveMenuCategory::getMenuCategoryUUID, Function.identity()));

        for (MenuCategorySequence menuCategorySequence : menuCategorySequenceUpdate.getMenuCategorySequenceList()) {
            DineReserveMenuCategory dineReserveMenuCategory = dineReserveMenuCategoryMap.get(menuCategorySequence.getMenuCategoryUUID());
            if (dineReserveMenuCategory != null) dineReserveMenuCategory.updateCategorySequence(menuCategorySequence.getMenuCategorySequence());
        }
    }

    public List<MenuCategoryListAll> menuCategoryListAll(UUID storeUUID) {
        return dineReserveMenuCategoryRepository.findMenuCategoryListAll(storeUUID);
    }
}
