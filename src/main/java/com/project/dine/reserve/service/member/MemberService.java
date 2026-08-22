package com.project.dine.reserve.service.member;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.member.DineReserveMember;
import com.project.dine.reserve.dto.constant.error.AuthErrorCode;
import com.project.dine.reserve.dto.constant.error.MemberErrorCode;
import com.project.dine.reserve.dto.member.*;
import com.project.dine.reserve.repository.member.DineReserveMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final DineReserveMemberRepository dineReserveMemberRepository;

    @Transactional
    public void memberSignup(MemberSignup memberSignup) {
        dineReserveMemberRepository.findByMemberID(memberSignup.getMemberID()).ifPresent(m -> {
            throw new DineReserveException(MemberErrorCode.EXIST_ID);
        });

        DineReserveMember dineReserveMember = DineReserveMember.create(memberSignup, passwordEncoder.encode(memberSignup.getMemberPassword()));
        dineReserveMemberRepository.save(dineReserveMember);
    }

    @Transactional
    public void memberUpdate(MemberUpdate memberUpdate) {
        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberUUID(memberUpdate.getMemberUUID())
                .orElseThrow(() -> new DineReserveException(MemberErrorCode.NO_MEMBER));

        dineReserveMember.update(memberUpdate);
    }

    @Transactional
    public void memberDelete(UUID memberUUID) {
        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberUUID(memberUUID)
                .orElseThrow(() -> new DineReserveException(MemberErrorCode.NO_MEMBER));

        dineReserveMember.updateUseFlag(false);
    }

    @Transactional
    public void memberUpdatePassword(MemberUpdatePassword memberUpdatePassword) {
        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberUUID(memberUpdatePassword.getMemberUUID())
                .orElseThrow(() -> new DineReserveException(MemberErrorCode.NO_MEMBER));

        if (!passwordEncoder.matches(memberUpdatePassword.getOriginPassword(), dineReserveMember.getMemberPassword())) throw new DineReserveException(AuthErrorCode.NOT_MATCH_PASSWORD);

        dineReserveMember.updatePassword(passwordEncoder.encode(memberUpdatePassword.getNewPassword()));
    }

    @Transactional
    public void memberUpdateStatus(MemberUpdateStatus memberUpdateStatus) {
        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberUUID(memberUpdateStatus.getMemberUUID())
                .orElseThrow(() -> new DineReserveException(MemberErrorCode.NO_MEMBER));

        dineReserveMember.updateStatus(memberUpdateStatus.getMemberStatus());
    }

    public Page<MemberList> memberListPage(String searchType, String searchValue, Long offset, int limit) {
        if (searchType == null) searchType = "";
        if (searchValue == null) searchValue = "";

        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return dineReserveMemberRepository.findMemberListPage(searchType, searchValue, offset, limit, pageable);
    }

    public MemberInfo memberInfo(UUID memberUUID) {
        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberUUID(memberUUID)
                .orElseThrow(() -> new DineReserveException(MemberErrorCode.NO_MEMBER));

        return MemberInfo.create(dineReserveMember);
    }
}
