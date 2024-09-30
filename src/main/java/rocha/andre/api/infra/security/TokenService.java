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
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.access.secret}")
    private String accessSecret;

    @Value("${api.security.refresh.secret}")
    private String refreshSecret;

    public String generateJwtToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);
            String token = JWT.create()
                    .withIssuer("geekcatalog-api")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId().toString())
                    .withExpiresAt(dateExpires())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating JWT accessToken", exception);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            return JWT.create()
                    .withIssuer("geekcatalog-api")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId().toString())
                    .withClaim("refreshId", UUID.randomUUID().toString())
                    .withExpiresAt(refreshTokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating refresh accessToken", exception);
        }
    }

    public boolean isJwtTokenValid(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("geekcatalog-api")
                    .build();

            DecodedJWT jwt = verifier.verify(tokenJwt);

            return true;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            // IllegalArgumentException é lançada se o tokenJwt for nulo ou vazio
            return false;
        }
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            JWT.require(algorithm).withIssuer("geekcatalog-api").build().verify(refreshToken);
            return true;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            return false;
        }
    }

    /*
    public String refreshJwtToken(String refreshToken) {
        try {
            if (!isRefreshTokenValid(refreshToken)) {
                throw new RuntimeException("Invalid refresh accessToken");
            }

            DecodedJWT decodedJWT = JWT.decode(refreshToken);
            String userId = decodedJWT.getClaim("id").asString();
            String userLogin = decodedJWT.getSubject();

            User user = findUserByIdOrLogin(userId, userLogin);
            return generateJwtToken(user);

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired refresh accessToken.", exception);
        }
    }
    */

    public String getSubject(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);
            String userVerified = JWT.require(algorithm)
                    .withIssuer("geekcatalog-api")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

            return userVerified;
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT accessToken.");
        }
    }

    public String getClaim(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);
            String userVerifiedId = String.valueOf(JWT.require(algorithm)
                    .withIssuer("geekcatalog-api")
                    .build()
                    .verify(tokenJwt)
                    .getClaim("id"));

            return userVerifiedId;
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT accessToken.");
        }
    }

    private Instant dateExpires() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant refreshTokenExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
