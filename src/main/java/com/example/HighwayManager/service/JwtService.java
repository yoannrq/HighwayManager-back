package com.example.HighwayManager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final String environment = System.getenv("ENVIRONMENT");

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractEmail(token);
        Date expiration = extractAllClaims(token).getExpiration();
        boolean isTokenExpired = expiration.before(new Date());

        return (username.equals(userDetails.getUsername())) && !isTokenExpired;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
        String role = authority.getAuthority();
        claims.put("role", role);

        long duration;
        ChronoUnit unit;

        if ("production".equalsIgnoreCase(environment)) {
            duration = 1;
            unit = ChronoUnit.DAYS;
        } else {
            duration = 5;
            unit = ChronoUnit.MINUTES;
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(duration, unit)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
