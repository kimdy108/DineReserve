package com.project.dine.reserve.repository.menu;

import com.project.dine.reserve.domain.menu.DineReserveMenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveMenuInfoRepository extends JpaRepository<DineReserveMenuInfo, Long>, DineReserveMenuInfoRepositoryCustom {
    Optional<DineReserveMenuInfo> findByMenuInfoUUID(UUID menuInfoUUID);

    List<DineReserveMenuInfo> findAllByMenuCategoryUUID(UUID menuCategoryUUID);

    @Query(value = "select colease(max(menuInfoSequence), 0) from DineReserveMenuInfo where menuCategoryUUID = :menuCategoryUUID")
    int countMaxSequenceByMenuCategoryUUID(UUID menuCategoryUUID);

    @Query(value = "select count(seq) from DineReserveMenuInfo where menuCategoryUUID = :menuCategoryUUID")
    int countByMenuCategoryUUID(UUID menuCategoryUUID);
}
