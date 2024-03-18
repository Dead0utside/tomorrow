package com.dead0uts1de.tomorrow.security.jwt;

import com.dead0uts1de.tomorrow.security.config.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {
    public String generateToken(Authentication authentication) {
        // NOTE: in context of JWT, email is considered as username
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(SecurityConstants.JWT_SECRET, Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SecurityConstants.JWT_SECRET)
                .build()
                .parseEncryptedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SecurityConstants.JWT_SECRET)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException("Token is expired or invalid");
        }
    }
}
