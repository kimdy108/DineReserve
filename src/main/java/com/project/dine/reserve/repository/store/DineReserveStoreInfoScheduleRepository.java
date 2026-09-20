package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreInfoSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface DineReserveStoreInfoScheduleRepository extends JpaRepository<DineReserveStoreInfoSchedule, Long>, DineReserveStoreInfoScheduleRepositoryCustom {
    List<DineReserveStoreInfoSchedule> findAllByStoreUUID(UUID storeUUID);

    @Query(value = "delete from DineReserveStoreInfoSchedule where storeUUID = :storeUUID")
    @Modifying
    @Transactional
    void deleteByStoreUUID(UUID storeUUID);
}
