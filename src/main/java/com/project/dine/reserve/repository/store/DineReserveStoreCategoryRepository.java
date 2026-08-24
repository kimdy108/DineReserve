package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveStoreCategoryRepository extends JpaRepository<DineReserveStoreCategory, Long>, DineReserveStoreCategoryRepositoryCustom {
    Optional<DineReserveStoreCategory> findByCategoryUUID(UUID categoryUUID);

    Optional<DineReserveStoreCategory> findByCategoryName(String categoryName);
}
