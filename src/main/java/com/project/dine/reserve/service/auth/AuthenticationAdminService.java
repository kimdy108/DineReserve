package com.project.dine.reserve.service.auth;

import com.project.dine.reserve.config.exception.DineReserveException;
import com.project.dine.reserve.domain.admin.DineReserveAdmin;
import com.project.dine.reserve.dto.auth.admin.*;
import com.project.dine.reserve.dto.common.RedisLoginSession;
import com.project.dine.reserve.dto.constant.error.AuthErrorCode;
import com.project.dine.reserve.repository.admin.DineReserveAdminRepository;
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
public class AuthenticationAdminService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    private final RedisService redisService;

    private final DineReserveAdminRepository dineReserveAdminRepository;

    @Transactional
    public AdminLoginAuth adminLogin(AdminLogin adminLogin, HttpServletRequest request) {
        DineReserveAdmin dineReserveAdmin = dineReserveAdminRepository.findByAdminID(adminLogin.getAdminID())
                .orElseThrow(() -> new DineReserveException(AuthErrorCode.AUTH_FAIL));
        if (!dineReserveAdmin.isUseFlag()) throw new DineReserveException(AuthErrorCode.AUTH_FAIL);
        if (!passwordEncoder.matches(adminLogin.getAdminPassword(), dineReserveAdmin.getAdminPassword())) throw new DineReserveException(AuthErrorCode.AUTH_FAIL);

        String accessToken = jwtUtil.createAdminToken(dineReserveAdmin.getAdminUUID(), dineReserveAdmin.getAdminRole());
        String refreshToken = jwtUtil.createRefreshToken(dineReserveAdmin.getAdminID());
        UUID sessionUUID = UUID.randomUUID();
        AdminLoginResult loginResult = AdminLoginResult.create(dineReserveAdmin.getAdminUUID(), sessionUUID);

        // refresh token, 로그인 리스트 저장
        redisService.setValues("LOGIN|@|" + dineReserveAdmin.getAdminID() + "|@|" + sessionUUID, refreshToken);
        redisService.setHashValues("LOGIN|@|" + dineReserveAdmin.getAdminID(), sessionUUID.toString(), RedisLoginSession.create(request));

        // 마지막 로그인 시간 수정
        dineReserveAdmin.updateLastDate();

        return AdminLoginAuth.create(accessToken, refreshToken, loginResult);
    }

    @Transactional
    public AdminRefreshAuth adminRefresh(AdminRefresh adminRefresh) {
        String savedToken = redisService.getValues("LOGIN|@|" + adminRefresh.getAdminID() + "|@|" + adminRefresh.getSessionUUID());
        if (!savedToken.equals(adminRefresh.getRefreshToken())) throw new DineReserveException(AuthErrorCode.REFRESH_AUTH_FAIL);

        DineReserveAdmin dineReserveAdmin = dineReserveAdminRepository.findByAdminID(adminRefresh.getAdminID())
                .orElseThrow(() -> new DineReserveException(AuthErrorCode.REFRESH_AUTH_FAIL));

        String accessToken = jwtUtil.createAdminToken(dineReserveAdmin.getAdminUUID(), dineReserveAdmin.getAdminRole());
        String refreshToken = jwtUtil.createRefreshToken(dineReserveAdmin.getAdminID());

        // 로그인 리스트 expire timestamp 수정
        Map<String, RedisLoginSession> hashValues = redisService.getHashValues("LOGIN|@|" + dineReserveAdmin.getAdminID());
        RedisLoginSession redisLoginSession = hashValues.get(adminRefresh.getSessionUUID().toString());
        redisLoginSession.setExpireTimestamp(Timestamp.valueOf(LocalDateTime.now().plusDays(7)).getTime());

        // refresh token, 로그인 리스트 삭제
        redisService.deleteValues("LOGIN|@|" + dineReserveAdmin.getAdminID() + "|@|" + adminRefresh.getSessionUUID());
        redisService.deleteHashValues("LOGIN|@|" + dineReserveAdmin.getAdminID(), adminRefresh.getSessionUUID().toString());

        // refresh token, 로그인 리스트 저장
        redisService.setValues("LOGIN|@|" + dineReserveAdmin.getAdminID() + "|@|" + adminRefresh.getSessionUUID(), refreshToken);
        redisService.setHashValues("LOGIN|@|" + dineReserveAdmin.getAdminID(), adminRefresh.getSessionUUID().toString(), redisLoginSession);

        return AdminRefreshAuth.create(accessToken, refreshToken);
    }
}
