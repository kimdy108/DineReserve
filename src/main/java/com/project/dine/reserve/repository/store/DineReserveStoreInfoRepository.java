package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveStoreInfoRepository extends JpaRepository<DineReserveStoreInfo, Long>, DineReserveStoreInfoRepositoryCustom {
    Optional<DineReserveStoreInfo> findByStoreUUID(UUID storeUUID);

    Optional<DineReserveStoreInfo> findByStoreName(String storeName);

    @Query(value = "select count(seq) from DineReserveStoreInfo where categoryUUID = :categoryUUID")
    int countByCategoryUUID(UUID categoryUUID);
}
