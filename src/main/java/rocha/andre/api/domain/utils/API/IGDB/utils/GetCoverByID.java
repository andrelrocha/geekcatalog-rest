package rocha.andre.api.domain.utils.API.IGDB.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CoverInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.ReleaseDateInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class GetCoverByID {
    private static final String RELEASE_DATES_URL = "https://api.igdb.com/v4/covers";
    private final RestTemplate restTemplate;

    public GetCoverByID(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String processCoverId(long coverId, HttpHeaders headers) {
        var releaseDateBody = "fields url; where id = " + coverId + ";";
        HttpEntity<String> requestEntity = new HttpEntity<>(releaseDateBody, headers);

        try {
            ResponseEntity<List<CoverInfo>> response = restTemplate.exchange(
                    RELEASE_DATES_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<List<CoverInfo>>() {}
            );

            if (response.getBody() != null && !response.getBody().isEmpty()) {
                return response.getBody().get(0).url();
            } else {
                throw new RuntimeException("An error occurred while fetching game cover on IGDB API");
            }

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("An error occurred while fetching game cover on IGDB API", e);
        }
    }
}
