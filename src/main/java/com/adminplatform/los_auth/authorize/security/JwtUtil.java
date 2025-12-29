package com.adminplatform.los_auth.authorize.security;

import com.adminplatform.los_auth.authorize.entity.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${spring.security.jwt.secret}")
    private String base64Secret;

    @Value("${spring.security.jwt.expiry-ms}")
    private Long jwtExpiryMs;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 256 bits (32 bytes)");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserAccount user, List<String> roles) {
        Key signingKey = getSigningKey();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiryMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getUserId().toString())     // ✅ camelCase
                .claim("tenantId", user.getTenantId().toString()) // ✅ camelCase
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserAccount user) {
        final String email = extractEmail(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
