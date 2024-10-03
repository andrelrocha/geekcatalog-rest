package rocha.andre.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import rocha.andre.api.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.access.secret}")
    private String accessSecret;

    @Value("${api.security.refresh.secret}")
    private String refreshSecret;

    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);

            // Convertendo authorities para uma lista de Strings
            List<String> authorities = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            // Criando o token e incluindo as authorities
            String token = JWT.create()
                    .withIssuer("geekcatalog-api")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId().toString())
                    .withClaim("role", user.getRole().toString())
                    //.withClaim("theme", user.getTheme().getName())
                    .withClaim("authorities", authorities)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(dateExpires())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
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
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(refreshTokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating refresh accessToken", exception);
        }
    }

    public boolean isAccessTokenValid(String tokenJwt) {
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
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("geekcatalog-api").build();
            DecodedJWT jwt = verifier.verify(refreshToken);

            return true;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            return false;
        }
    }

    public String refreshJwtToken(String refreshToken, String accessToken) {
        try {
            if (isRefreshTokenValid(refreshToken)) {
                Algorithm algorithm = Algorithm.HMAC256(accessSecret);

                return JWT.create()
                        .withIssuer("geekcatalog-api")
                        .withSubject(getSubject(accessToken))
                        .withClaim("id", getClaim(accessToken))
                        .withClaim("authorities", getClaim(accessToken))
                        .withClaim("theme", getClaim(accessToken))
                        .withIssuedAt(Instant.now())
                        .withClaim("refreshedAt", Instant.now().toString())
                        .withExpiresAt(dateExpires())
                        .sign(algorithm);
            } else {
                throw new RuntimeException("Invalid or expired refresh token.");
            }
        } catch (JWTVerificationException exception) {
            System.out.println("Error during JWT verification: " + exception.getMessage());
            throw new RuntimeException("Invalid or expired refresh token.", exception);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            throw e;
        }
    }

    public List<String> getAuthorities(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessSecret))
                .withIssuer("geekcatalog-api")
                .build()
                .verify(token);

        // Recupera a claim "authorities" como uma lista de strings
        return decodedJWT.getClaim("authorities").asList(String.class);
    }

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
        //return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
        return LocalDateTime.now().plusSeconds(30).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant refreshTokenExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
