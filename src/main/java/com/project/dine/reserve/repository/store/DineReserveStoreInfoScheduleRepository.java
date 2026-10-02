package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreInfoSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveStoreInfoScheduleRepository extends JpaRepository<DineReserveStoreInfoSchedule, Long>, DineReserveStoreInfoScheduleRepositoryCustom {
    Optional<DineReserveStoreInfoSchedule> findByStoreUUIDAndDayOfWeek(UUID storeUUID, DayOfWeek dayOfWeek);

    List<DineReserveStoreInfoSchedule> findAllByStoreUUID(UUID storeUUID);

    void deleteAllByStoreUUID(UUID storeUUID);
}
