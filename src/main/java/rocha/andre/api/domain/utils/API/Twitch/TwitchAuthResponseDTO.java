package rocha.andre.api.domain.utils.API.Twitch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TwitchAuthResponseDTO(@JsonProperty("access_token") String accessToken,
                                    @JsonProperty("expires_in") int expiresIn,
                                    @JsonProperty("token_type") String tokenType) {
}
