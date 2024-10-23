package rocha.andre.api.domain.utils.API.IGDB;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.GenreInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetGenresNameByID {
    private static final String GENRES_URL = "https://api.igdb.com/v4/genres";
    private final RestTemplate restTemplate;
    public GetGenresNameByID(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> processGenres(List<Integer> genreIds, HttpHeaders headers) {
        List<String> genreNames = new ArrayList<>();
        if (genreIds != null && !genreIds.isEmpty()) {
            String genreBody = "fields name; where id = (" +
                    String.join(",", genreIds.stream().map(String::valueOf).toArray(String[]::new)) +
                    ");";

            HttpEntity<String> requestEntity = new HttpEntity<>(genreBody, headers);

            ResponseEntity<List<GenreInfo>> genreResponse = restTemplate.exchange(
                    GENRES_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<List<GenreInfo>>() {}
            );

            if (genreResponse.getBody() != null) {
                for (GenreInfo genre : genreResponse.getBody()) {
                    genreNames.add(genre.name());
                }
            }
        }
        return genreNames;
    }
}
