package com.dead0uts1de.tomorrow.security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class SecurityConstants {
//    @Value("${jwt.token.secret}")
//    private static String jwtSecret;

    public static final long JWT_EXPIRATION = 70000;
//    public static final SecretKey JWT_SECRET = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    public static final SecretKey JWT_SECRET = Jwts.SIG.HS512.key().build();
}
