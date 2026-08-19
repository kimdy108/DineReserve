package com.project.dine.reserve.repository.system;

import com.project.dine.reserve.domain.system.DineReserveFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveFileRepository extends JpaRepository<DineReserveFile, Long> {
    Optional<DineReserveFile> findByFileUUID(UUID fileUUID);
}
