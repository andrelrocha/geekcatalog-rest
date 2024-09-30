package rocha.andre.api.infra.security;

public record TokenJwtDto(String accessToken, String refreshToken) {
}
