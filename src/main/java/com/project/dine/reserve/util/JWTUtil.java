package com.project.dine.reserve.util;

import com.project.dine.reserve.dto.constant.admin.AdminRole;
import com.project.dine.reserve.dto.constant.member.MemberRole;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtil {
    private final Long expirationTime;
    private final SecretKey secretKey;

    public JWTUtil(@Value("${jwt.token.secret}") String secret,
                   @Value("${jwt.token.expiration_time}") Long expirationTime) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expirationTime = expirationTime;
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String createAdminToken(UUID userUUID, AdminRole adminRole) {
        return Jwts.builder()
                .claim("userUUID", userUUID)
                .claim("role", adminRole)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String createMemberToken(UUID userUUID) {
        return Jwts.builder()
                .claim("userUUID", userUUID)
                .claim("role", MemberRole.MEMBER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userID) {
        return Jwts.builder()
                .claim("userID", userID)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 24 * 7))
                .signWith(secretKey)
                .compact();
    }
}
