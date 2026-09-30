package com.project.dine.reserve.service.auth;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.member.DineReserveGuest;
import com.project.dine.reserve.dto.auth.member.*;
import com.project.dine.reserve.dto.common.RedisLoginSession;
import com.project.dine.reserve.dto.constant.error.AuthErrorCode;
import com.project.dine.reserve.repository.member.DineReserveGuestRepository;
import com.project.dine.reserve.service.component.RedisService;
import com.project.dine.reserve.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationGuestService {
    private final JWTUtil jwtUtil;

    private final RedisService redisService;

    private final DineReserveGuestRepository dineReserveGuestRepository;

    @Transactional
    public GuestLoginAuth guestLogin(GuestLogin guestLogin, HttpServletRequest request) {
        DineReserveGuest dineReserveGuest = dineReserveGuestRepository.findByGuestPhoneAndGuestPassword(guestLogin.getGuestPhone(), guestLogin.getGuestPassword())
                .orElseGet(() -> dineReserveGuestRepository.save(DineReserveGuest.create(guestLogin)));

        String accessToken = jwtUtil.createMemberToken(dineReserveGuest.getGuestUUID());
        String refreshToken = jwtUtil.createRefreshToken(dineReserveGuest.getGuestPhone());
        UUID sessionUUID = UUID.randomUUID();
        GuestLoginResult loginResult = GuestLoginResult.create(dineReserveGuest.getGuestUUID(), sessionUUID);

        // refresh token, 로그인 리스트 저장
        redisService.setValues("LOGIN|@|" + dineReserveGuest.getGuestUUID() + "|@|" + sessionUUID, refreshToken);
        redisService.setHashValues("LOGIN|@|" + dineReserveGuest.getGuestUUID(), sessionUUID.toString(), RedisLoginSession.create(request));

        // 마지막 로그인 시간 수정
        dineReserveGuest.updateLastDate();

        return GuestLoginAuth.create(accessToken, refreshToken, loginResult);
    }

    @Transactional
    public GuestRefreshAuth guestRefresh(GuestRefresh guestRefresh) {
        String savedRefreshToken = redisService.getValues("LOGIN|@|" + guestRefresh.getGuestUUID() + "|@|" + guestRefresh.getSessionUUID());
        if (!savedRefreshToken.equals(guestRefresh.getRefreshToken())) throw new DineReserveException(AuthErrorCode.REFRESH_AUTH_FAIL);

        DineReserveGuest dineReserveGuest = dineReserveGuestRepository.findByGuestUUID(guestRefresh.getGuestUUID())
                .orElseThrow(() -> new DineReserveException(AuthErrorCode.REFRESH_AUTH_FAIL));

        String accessToken = jwtUtil.createMemberToken(dineReserveGuest.getGuestUUID());
        String refreshToken = jwtUtil.createRefreshToken(dineReserveGuest.getGuestPhone());

        // 로그인 리스트 expire timestamp 수정
        Map<String, RedisLoginSession> hashValues = redisService.getHashValues("LOGIN|@|" + dineReserveGuest.getGuestUUID());
        RedisLoginSession redisLoginSession = hashValues.get(guestRefresh.getSessionUUID().toString());
        redisLoginSession.setExpireTimestamp(Timestamp.valueOf(LocalDateTime.now().plusDays(7)).getTime());

        // refresh token, 로그인 리스트 삭제
        redisService.deleteValues("LOGIN|@|" + dineReserveGuest.getGuestUUID() + "|@|" + guestRefresh.getSessionUUID());
        redisService.deleteHashValues("LOGIN|@|" + dineReserveGuest.getGuestUUID(), guestRefresh.getSessionUUID().toString());

        // refresh token, 로그인 리스트 저장
        redisService.setValues("LOGIN|@|" + dineReserveGuest.getGuestUUID() + "|@|" + guestRefresh.getSessionUUID(), refreshToken);
        redisService.setHashValues("LOGIN|@|" + dineReserveGuest.getGuestUUID(), guestRefresh.getSessionUUID().toString(), redisLoginSession);

        return GuestRefreshAuth.create(accessToken, refreshToken);
    }
}
