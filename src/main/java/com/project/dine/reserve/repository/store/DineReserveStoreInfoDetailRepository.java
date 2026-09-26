package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreInfoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveStoreInfoDetailRepository extends JpaRepository<DineReserveStoreInfoDetail, Long> {
    Optional<DineReserveStoreInfoDetail> findByStoreUUID(UUID storeUUID);
}
