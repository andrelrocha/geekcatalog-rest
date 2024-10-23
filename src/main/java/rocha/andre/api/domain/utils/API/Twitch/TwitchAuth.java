package rocha.andre.api.domain.utils.API.Twitch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryInfoDTO;

import java.util.Objects;

@Component
public class TwitchAuth {
    @Value("${twitch.client-id}")
    private String clientId;

    @Value("${twitch.client-secret}")
    private String clientSecret;

    @Value("${twitch.auth-url}")
    private String authUrl;

    public IGDBQueryInfoDTO authenticateUser(String gameName) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        var restTemplate = new RestTemplate();

        ResponseEntity<TwitchAuthResponseDTO> response = restTemplate.exchange(
                authUrl, HttpMethod.POST, requestEntity, TwitchAuthResponseDTO.class);

        var accessToken = Objects.requireNonNull(response.getBody()).accessToken();

        return new IGDBQueryInfoDTO(gameName, clientId, accessToken);
    }
}