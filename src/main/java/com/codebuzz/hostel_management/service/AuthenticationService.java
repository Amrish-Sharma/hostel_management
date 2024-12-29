package com.codebuzz.hostel_management.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class AuthenticationService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    /**
     * Performs authentication using a JWT token.
     * @param jwtToken the token to authenticate
     */
    public void performAuthentication(String jwtToken) {
        try {
            // Parse the JWT token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Extract information from the claims
            String username = claims.getSubject(); // Typically the username is the 'subject'
            String roles = (String) claims.get("roles"); // Custom fields can be added (e.g., roles)

            // Perform further checks (e.g., verify username exists in DB, roles match, etc.)
            System.out.println("Authenticated user: " + username);
            System.out.println("Roles: " + roles);

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

    /**
     * Generates the signing key from the secret key.
     * @return Key for signing the JWT
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
