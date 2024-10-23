package rocha.andre.api.domain.utils.API.IGDB;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.GameInfo;

import java.util.List;

@Component
public class GetGameInfoByName {
    private static final String GAMES_URL = "https://api.igdb.com/v4/games";
    private final RestTemplate restTemplate;

    public GetGameInfoByName(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<GameInfo>> fetchGameDetailsFromIGDB(String gameName, HttpHeaders headers) {
        String body = "fields name, genres, involved_companies, platforms; " +
                "where name = \"" + gameName + "\"; " +
                "limit 1; " +
                "sort aggregated_rating desc;";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                GAMES_URL, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<GameInfo>>() {}
        );
    }
}
