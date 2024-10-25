package rocha.andre.api.domain.utils.API.IGDB.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
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
        String body = "fields name, genres, involved_companies, platforms, release_dates; " +
                "where name = \"" + gameName + "\"; " +
                "limit 1; " +
                "sort aggregated_rating desc;";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(
                    GAMES_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<List<GameInfo>>() {}
            );
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error while fetching game details from IGDB: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        } catch (RestClientException e) {
            System.err.println("Client Error on fetch game details from IGDB: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw e;
        }
    }
}
