package com.workoutrack.WorkoutTracker.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.workoutrack.WorkoutTracker.domain.usuario.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class JwtTokenService {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.issuer}")
    private String issuer;

    @Value("${spring.jwt.expiration-hours}")
    private long expirationHours;

    public String generateToken(UserDetailsImpl user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername()) // email
                .withIssuedAt(Instant.now())
                .withExpiresAt(
                        Instant.now().plus(expirationHours, ChronoUnit.HOURS)
                )
                .withClaim("userId", user.getId().toString()) // UUID
                .sign(algorithm);
    }

    public UUID getUserIdFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String userId = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getClaim("userId")
                .asString();

        return UUID.fromString(userId);
    }

    public String getSubjectFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }
}
