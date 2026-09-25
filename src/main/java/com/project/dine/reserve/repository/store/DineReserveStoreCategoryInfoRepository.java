package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreCategoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DineReserveStoreCategoryInfoRepository extends JpaRepository<DineReserveStoreCategoryInfo, Long> {
    List<DineReserveStoreCategoryInfo> findAllByStoreUUID(UUID storeUUID);

    @Query(value = "select count(seq) from DineReserveStoreCategoryInfo where categoryUUID = :categoryUUID")
    int countByCategoryUUID(UUID categoryUUID);
}
