package com.project.dine.reserve.repository.member;

import com.project.dine.reserve.dto.member.MemberList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DineReserveMemberRepositoryCustom {
    Page<MemberList> findMemberListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable);
}
