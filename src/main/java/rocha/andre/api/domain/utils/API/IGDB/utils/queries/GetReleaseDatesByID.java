package rocha.andre.api.domain.utils.API.IGDB.utils.queries;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.ReleaseDateInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class GetReleaseDatesByID {
    private static final String RELEASE_DATES_URL = "https://api.igdb.com/v4/release_dates";
    private final RestTemplate restTemplate;

    public GetReleaseDatesByID(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int processReleaseDatesList(List<Long> releaseDateIds, HttpHeaders headers) {
        String releaseDateBody = "fields date; where id = (" +
                String.join(",", releaseDateIds.stream().map(String::valueOf).toArray(String[]::new)) +
                "); sort date asc; limit 1;";

        HttpEntity<String> requestEntity = new HttpEntity<>(releaseDateBody, headers);

        try {
            ResponseEntity<List<ReleaseDateInfo>> response = restTemplate.exchange(
                    RELEASE_DATES_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<List<ReleaseDateInfo>>() {}
            );

            if (response.getBody() != null && !response.getBody().isEmpty()) {
                long timestamp = response.getBody().get(0).date();
                return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()).getYear();
            } else {
                throw new RuntimeException("An error occurred while fetching game release year on IGDB API");
            }

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("An error occurred while fetching game release year on IGDB API", e);
        }
    }
}
