package com.dead0uts1de.tomorrow.security.config;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecurityConstants {
//    @Value("${jwt.token.secret}")
//    private static String jwtSecret;

    public static final long JWT_EXPIRATION = 700000;
//    public static final SecretKey JWT_SECRET = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    public static final SecretKey JWT_SECRET = Jwts.SIG.HS512.key().build();
}
