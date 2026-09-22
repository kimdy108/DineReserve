package com.project.dine.reserve.repository.menu;

import com.project.dine.reserve.domain.menu.DineReserveMenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveMenuCategoryRepository extends JpaRepository<DineReserveMenuCategory, Long>, DineReserveMenuCategoryRepositoryCustom {
    Optional<DineReserveMenuCategory> findByMenuCategoryUUID(UUID menuCategoryUUID);

    Optional<DineReserveMenuCategory> findByStoreUUIDAndMenuCategoryName(UUID storeUUID, String menuCategoryName);

    List<DineReserveMenuCategory> findAllByStoreUUID(UUID storeUUID);

    @Query(value = "select coalesce(max(menuCategorySequence), 0) from DineReserveMenuCategory where storeUUID = :storeUUID")
    int countMaxSequenceByStoreUUID(UUID storeUUID);
}
