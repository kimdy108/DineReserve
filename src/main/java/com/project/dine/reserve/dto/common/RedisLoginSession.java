package com.project.dine.reserve.dto.common;

import com.project.dine.reserve.util.Common;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisLoginSession {
    private String userIP;
    private String userOS;
    private String userBrowser;
    private LocalDateTime loginDate;
    private Long expireTimestamp;

    public static RedisLoginSession create(HttpServletRequest request) {
        RedisLoginSession redisLoginSession = new RedisLoginSession();
        redisLoginSession.setUserIP(Common.UserInfo.getUserIP(request));
        redisLoginSession.setUserOS(Common.UserInfo.getUserOS(request));
        redisLoginSession.setUserBrowser(Common.UserInfo.getUserBrowser(request));
        redisLoginSession.setLoginDate(LocalDateTime.now());
        redisLoginSession.setExpireTimestamp(Timestamp.valueOf(LocalDateTime.now().plusDays(7)).getTime());

        return redisLoginSession;
    }
}
