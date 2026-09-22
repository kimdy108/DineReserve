package com.project.dine.reserve.repository.menu;

import com.project.dine.reserve.dto.menu.category.MenuCategoryListAll;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DineReserveMenuCategoryRepositoryCustom {
    List<MenuCategoryListAll> findMenuCategoryListAll(UUID storeUUID, boolean isTotal);
}
