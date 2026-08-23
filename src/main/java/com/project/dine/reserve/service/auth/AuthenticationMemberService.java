package com.project.dine.reserve.service.auth;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.member.DineReserveMember;
import com.project.dine.reserve.dto.auth.member.*;
import com.project.dine.reserve.dto.common.RedisLoginSession;
import com.project.dine.reserve.dto.constant.error.AuthErrorCode;
import com.project.dine.reserve.dto.constant.member.MemberStatusType;
import com.project.dine.reserve.repository.member.DineReserveMemberRepository;
import com.project.dine.reserve.service.component.RedisService;
import com.project.dine.reserve.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationMemberService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    private final RedisService redisService;

    private final DineReserveMemberRepository dineReserveMemberRepository;

    @Transactional
    public MemberLoginAuth memberLogin(MemberLogin memberLogin, HttpServletRequest request) {
        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberID(memberLogin.getMemberID())
                .orElseThrow(() -> new DineReserveException(AuthErrorCode.AUTH_FAIL));
        if (!dineReserveMember.isUseFlag()) throw new DineReserveException(AuthErrorCode.AUTH_FAIL);
        if (!passwordEncoder.matches(memberLogin.getMemberPassword(), dineReserveMember.getMemberPassword())) throw new DineReserveException(AuthErrorCode.AUTH_FAIL);
        if (!MemberStatusType.ACTIVE.equals(dineReserveMember.getMemberStatus())) throw new DineReserveException(AuthErrorCode.AUTH_FAIL);

        String accessToken = jwtUtil.createMemberToken(dineReserveMember.getMemberUUID());
        String refreshToken = jwtUtil.createRefreshToken(dineReserveMember.getMemberID());
        UUID sessionUUID = UUID.randomUUID();
        MemberLoginResult loginResult = MemberLoginResult.create(dineReserveMember.getMemberUUID(),sessionUUID);

        // refresh token, 로그인 리스트 저장
        redisService.setValues("LOGIN|@|" + dineReserveMember.getMemberID() + "|@|" + sessionUUID, refreshToken);
        redisService.setHashValues("LOGIN|@|" + dineReserveMember.getMemberID(), sessionUUID.toString(), RedisLoginSession.create(request));

        // 마지막 로그인 시간 수정
        dineReserveMember.updateLastDate();

        return MemberLoginAuth.create(accessToken, refreshToken, loginResult);
    }

    @Transactional
    public MemberRefreshAuth memberRefresh(MemberRefresh memberRefresh) {
        String savedRefreshToken = redisService.getValues("LOGIN|@|" + memberRefresh.getMemberID() + "|@|" + memberRefresh.getSessionUUID());
        if (!savedRefreshToken.equals(memberRefresh.getRefreshToken())) throw new DineReserveException(AuthErrorCode.REFRESH_AUTH_FAIL);

        DineReserveMember dineReserveMember = dineReserveMemberRepository.findByMemberID(memberRefresh.getMemberID())
                .orElseThrow(() -> new DineReserveException(AuthErrorCode.REFRESH_AUTH_FAIL));

        String accessToken = jwtUtil.createMemberToken(dineReserveMember.getMemberUUID());
        String refreshToken = jwtUtil.createRefreshToken(dineReserveMember.getMemberID());

        // 로그인 리스트 expire timestamp 수정
        Map<String, RedisLoginSession> hashValues = redisService.getHashValues("LOGIN|@|" + dineReserveMember.getMemberID());
        RedisLoginSession redisLoginSession = hashValues.get(memberRefresh.getSessionUUID().toString());
        redisLoginSession.setExpireTimestamp(Timestamp.valueOf(LocalDateTime.now().plusDays(7)).getTime());

        // refresh token, 로그인 리스트 삭제
        redisService.deleteValues("LOGIN|@|" + dineReserveMember.getMemberID() + "|@|" + memberRefresh.getSessionUUID());
        redisService.deleteHashValues("LOGIN|@|" + dineReserveMember.getMemberID(), memberRefresh.getSessionUUID().toString());

        // refresh token, 로그인 리스트 저장
        redisService.setValues("LOGIN|@|" + dineReserveMember.getMemberID() + "|@|" + memberRefresh.getSessionUUID(), refreshToken);
        redisService.setHashValues("LOGIN|@|" + dineReserveMember.getMemberID(), memberRefresh.getSessionUUID().toString(), redisLoginSession);

        return MemberRefreshAuth.create(accessToken, refreshToken);
    }
}
