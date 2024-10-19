package rocha.andre.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TwitchAuthResponseDTO(@JsonProperty("access_token") String accessToken,
                                    @JsonProperty("expires_in") int expiresIn,
                                    @JsonProperty("token_type") String tokenType) {
}
