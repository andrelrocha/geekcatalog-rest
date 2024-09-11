package rocha.andre.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateJwtToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("andre rocha")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId().toString())
                    .withExpiresAt(dateExpires())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating JWT token", exception);
        }
    }

    public boolean isJwtTokenValid(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("andre rocha")
                    .build();

            DecodedJWT jwt = verifier.verify(tokenJwt);

            return true;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            // IllegalArgumentException é lançada se o tokenJwt for nulo ou vazio
            return false;
        }
    }

    public String getSubject(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String userVerified = JWT.require(algorithm)
                    .withIssuer("andre rocha")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

            return userVerified;
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT token.");
        }
    }

    public String getClaim(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String userVerifiedId = String.valueOf(JWT.require(algorithm)
                    .withIssuer("andre rocha")
                    .build()
                    .verify(tokenJwt)
                    .getClaim("id"));

            return userVerifiedId;
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT token.");
        }
    }

    private Instant dateExpires() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
