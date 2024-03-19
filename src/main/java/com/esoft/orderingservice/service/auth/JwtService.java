package com.esoft.orderingservice.service.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.esoft.orderingservice.exception.JwtInvalidException;
import com.esoft.orderingservice.repository.TokenRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    private final TokenRepo tokenRepo;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000)) // 1 hour
                .signWith(getSigningKey())
                .compact();
    }

    public void validateToken(String token) {
        if (isTokenExpired(token)) {
            throw new JwtInvalidException("The JWT token is expired");
        }

        boolean isLoggedOut = tokenRepo.findByToken(token)
                .map(t -> t.isLoggedOut())
                .orElse(true);

        if (isLoggedOut) {
            throw new JwtInvalidException("The JWT token is invalid");
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new JwtInvalidException("The JWT token is invalid");
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
