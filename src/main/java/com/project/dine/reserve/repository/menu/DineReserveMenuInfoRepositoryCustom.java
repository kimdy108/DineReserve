package com.project.dine.reserve.repository.menu;

import com.project.dine.reserve.dto.menu.info.MenuInfoListAll;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DineReserveMenuInfoRepositoryCustom {
    List<MenuInfoListAll> findMenuInfoListAll(UUID storeUUID, UUID menuCategoryUUID, boolean isTotal);
}
