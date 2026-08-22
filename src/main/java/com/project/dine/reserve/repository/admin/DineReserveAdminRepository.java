package com.project.dine.reserve.repository.admin;

import com.project.dine.reserve.domain.admin.DineReserveAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveAdminRepository extends JpaRepository<DineReserveAdmin, Long>, DineReserveAdminRepositoryCustom {
    Optional<DineReserveAdmin> findByAdminUUID(UUID adminUUID);

    Optional<DineReserveAdmin> findByAdminID(String adminID);
}
