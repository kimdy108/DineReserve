package com.project.dine.reserve.repository.member;

import com.project.dine.reserve.domain.member.QDineReserveMember;
import com.project.dine.reserve.dto.member.MemberList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DineReserveMemberRepositoryImpl implements DineReserveMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public DineReserveMemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QDineReserveMember qDineReserveMember = QDineReserveMember.dineReserveMember;

    @Override
    public Page<MemberList> findMemberListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable) {
        Long setOffset = offset * limit;

        OrderSpecifier<?> sortedColumn = qDineReserveMember.seq.desc();

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qDineReserveMember.useFlag.eq(true));

        List<MemberList> memberLists = jpaQueryFactory
                .select(Projections.fields(
                        MemberList.class,
                        qDineReserveMember.memberUUID.as("memberUUID"),
                        qDineReserveMember.memberID.as("memberID"),
                        qDineReserveMember.memberName.as("memberName"),
                        qDineReserveMember.memberEmail.as("memberEmail"),
                        qDineReserveMember.memberStatus.as("memberStatus")
                ))
                .from(qDineReserveMember)
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue), eqMemberPhone(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qDineReserveMember.seq.count())
                .from(qDineReserveMember)
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue), eqMemberPhone(searchType, searchValue));

        return PageableExecutionUtils.getPage(memberLists, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqMemberID(String searchType, String searchValue) {
        if (!searchType.equals("memberID") || "".equals(searchValue)) return null;
        return qDineReserveMember.memberID.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqMemberName(String searchType, String searchValue) {
        if (!searchType.equals("memberName") || "".equals(searchValue)) return null;
        return qDineReserveMember.memberName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqMemberPhone(String searchType, String searchValue) {
        if (!searchType.equals("memberPhone") || "".equals(searchValue)) return null;
        return qDineReserveMember.memberPhone.containsIgnoreCase(searchValue);
    }
}
