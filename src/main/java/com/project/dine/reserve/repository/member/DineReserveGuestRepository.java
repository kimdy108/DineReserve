package com.project.dine.reserve.repository.member;

import com.project.dine.reserve.domain.member.DineReserveGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveGuestRepository extends JpaRepository<DineReserveGuest, Long> {
    Optional<DineReserveGuest> findByGuestUUID(UUID guestUUID);

    Optional<DineReserveGuest> findByGuestPhoneAndGuestPassword(String guestPhone, String GuestPassword);
}
