package com.Aptech.userservice.Configs;

import java.time.Duration;
import java.util.Date;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RsaKeyUtil rsaKeyUtil;
    private final StringRedisTemplate redis;

    public String generateToken(String userId, long expiryMillis) throws InvalidKeyException, Exception {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryMillis))
                .signWith(rsaKeyUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    public String getUserIdFromToken(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(rsaKeyUtil.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            getUserIdFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void blacklistToken(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, IllegalArgumentException, Exception {
        long expiry = getExpirationTime(token) - System.currentTimeMillis();
        redis.opsForValue().set("blacklist:" + token, "1", Duration.ofMillis(expiry));
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redis.hasKey("blacklist:" + token));
    }

    public long getExpirationTime(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, IllegalArgumentException, Exception {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(rsaKeyUtil.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().getTime();
    }
}
