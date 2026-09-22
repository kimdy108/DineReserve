package com.project.dine.reserve.service.menu;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.menu.DineReserveMenuCategory;
import com.project.dine.reserve.domain.menu.DineReserveMenuInfo;
import com.project.dine.reserve.domain.system.DineReserveFile;
import com.project.dine.reserve.dto.constant.error.MenuErrorCode;
import com.project.dine.reserve.dto.menu.info.*;
import com.project.dine.reserve.repository.menu.DineReserveMenuCategoryRepository;
import com.project.dine.reserve.repository.menu.DineReserveMenuInfoRepository;
import com.project.dine.reserve.repository.system.DineReserveFileRepository;
import com.project.dine.reserve.service.component.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.project.dine.reserve.util.Common.EMPTY_UUID;

@Service
@RequiredArgsConstructor
public class MenuInfoService {
    private final FileService fileService;

    private final DineReserveFileRepository dineReserveFileRepository;

    private final DineReserveMenuCategoryRepository dineReserveMenuCategoryRepository;
    private final DineReserveMenuInfoRepository dineReserveMenuInfoRepository;

    @Transactional
    public void menuInfoRegist(MenuInfoRegist menuInfoRegist) {
        DineReserveMenuCategory dineReserveMenuCategory = dineReserveMenuCategoryRepository.findByMenuCategoryUUID(menuInfoRegist.getMenuCategoryUUID())
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_CATEGORY));

        DineReserveFile menuInfoImg = fileService.insertFile(menuInfoRegist.getMenuInfoImg(), "menu", "img");

        int maxSequence = dineReserveMenuInfoRepository.countMaxSequenceByMenuCategoryUUID(dineReserveMenuCategory.getMenuCategoryUUID());

        DineReserveMenuInfo dineReserveMenuInfo = DineReserveMenuInfo.create(menuInfoRegist, maxSequence, dineReserveMenuCategory, menuInfoImg);
        dineReserveMenuInfoRepository.save(dineReserveMenuInfo);
    }

    @Transactional
    public void menuInfoUpdate(MenuInfoUpdate menuInfoUpdate) {
        DineReserveMenuInfo dineReserveMenuInfo = dineReserveMenuInfoRepository.findByMenuInfoUUID(menuInfoUpdate.getMenuInfoUUID())
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_INFO));

        DineReserveFile menuInfoImg = menuInfoUpdate.getMenuInfoImg() == null ?
                dineReserveFileRepository.findByFileUUID(dineReserveMenuInfo.getMenuInfoImgUUID()).orElse(null) :
                fileService.updateFile(dineReserveMenuInfo.getMenuInfoImgUUID(), menuInfoUpdate.getMenuInfoImg(), "menu", "img");

        dineReserveMenuInfo.update(menuInfoUpdate, menuInfoImg);
    }

    @Transactional
    public void menuInfoDelete(UUID menuInfoUUID) {
        DineReserveMenuInfo dineReserveMenuInfo = dineReserveMenuInfoRepository.findByMenuInfoUUID(menuInfoUUID)
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_INFO));

        fileService.deleteFile(dineReserveMenuInfo.getMenuInfoImgUUID());

        dineReserveMenuInfoRepository.delete(dineReserveMenuInfo);
    }

    @Transactional
    public void menuInfoSequence(MenuInfoSequenceUpdate menuInfoSequenceUpdate) {
        List<DineReserveMenuInfo> dineReserveMenuInfoList = dineReserveMenuInfoRepository.findAllByMenuCategoryUUID(menuInfoSequenceUpdate.getMenuCategoryUUID());
        Map<UUID, DineReserveMenuInfo> dineReserveMenuInfoMap = dineReserveMenuInfoList.stream()
                .collect(Collectors.toMap(DineReserveMenuInfo::getMenuInfoUUID, Function.identity()));

        for (MenuInfoSequence menuInfoSequence : menuInfoSequenceUpdate.getMenuInfoSequenceList()) {
            DineReserveMenuInfo dineReserveMenuInfo = dineReserveMenuInfoMap.get(menuInfoSequence.getMenuInfoUUID());
            if (dineReserveMenuInfo != null) dineReserveMenuInfo.updateSequence(menuInfoSequence.getMenuInfoSequence());
        }
    }

    public List<MenuInfoListAll> menuInfoListAll(UUID storeUUID, UUID menuCategoryUUID) {
        if (menuCategoryUUID == null) menuCategoryUUID = EMPTY_UUID;

        return dineReserveMenuInfoRepository.findMenuInfoListAll(storeUUID, menuCategoryUUID, false);
    }

    public List<MenuInfoListAll> menuInfoListTotal(UUID storeUUID, UUID menuCategoryUUID) {
        if (menuCategoryUUID == null) menuCategoryUUID = EMPTY_UUID;

        return dineReserveMenuInfoRepository.findMenuInfoListAll(storeUUID, menuCategoryUUID, true);
    }

    public MenuInfoInfo menuInfoInfo(UUID menuInfoUUID) {
        DineReserveMenuInfo dineReserveMenuInfo = dineReserveMenuInfoRepository.findByMenuInfoUUID(menuInfoUUID)
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_INFO));

        DineReserveMenuCategory dineReserveMenuCategory = dineReserveMenuCategoryRepository.findByMenuCategoryUUID(dineReserveMenuInfo.getMenuCategoryUUID())
                .orElseThrow(() -> new DineReserveException(MenuErrorCode.NO_MENU_CATEGORY));

        return MenuInfoInfo.create(dineReserveMenuInfo, dineReserveMenuCategory);
    }
}
