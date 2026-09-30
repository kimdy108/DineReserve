package com.project.dine.reserve.repository.store;

import com.project.dine.reserve.domain.store.DineReserveStoreHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveStoreHolidayRepository extends JpaRepository<DineReserveStoreHoliday, Long>, DineReserveStoreHolidayRepositoryCustom {
    Optional<DineReserveStoreHoliday> findByHolidayUUID(UUID holidayUUID);

    void deleteAllByStoreUUID(UUID storeUUID);
}
