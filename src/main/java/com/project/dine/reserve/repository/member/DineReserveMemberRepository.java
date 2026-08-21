package com.project.dine.reserve.repository.member;

import com.project.dine.reserve.domain.member.DineReserveMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DineReserveMemberRepository extends JpaRepository<DineReserveMember, Long>, DineReserveMemberRepositoryCustom {
    Optional<DineReserveMember> findByMemberUUID(UUID memberUUID);

    Optional<DineReserveMember> findByMemberID(String memberID);
}
