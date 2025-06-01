package com.Aptech.testservice.Configs;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RsaKeyUtil rsaKeyUtil;

    // public String generateToken(String userId, long expiryMillis) throws
    // InvalidKeyException, Exception {
    // return Jwts.builder()
    // .setSubject(userId)
    // .setIssuedAt(new Date())
    // .setExpiration(new Date(System.currentTimeMillis() + expiryMillis))
    // .signWith(rsaKeyUtil.getPrivateKey(), SignatureAlgorithm.RS256)
    // .compact();
    // }

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
}
