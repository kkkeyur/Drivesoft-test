package com.drivesoft.testapi.utils.security;

import com.drivesoft.testapi.configs.ExampleProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

   private final ExampleProperties exampleProperties;

    // Token expiration time (e.g., 1 hours)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 1;

    // Generate a new JWT token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        var token  = createToken(claims, username);
        log.info("token created {}",token);
        return token;

    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, exampleProperties.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().
                setSigningKey(exampleProperties.getSecretKey()).
                parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

