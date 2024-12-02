package rocha.andre.api.domain.utils.API.IGDB.utils.queries;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.PlatformsInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetPlatformsNameByID {
    private static final String PLATFORMS_URL = "https://api.igdb.com/v4/platforms";
    private final RestTemplate restTemplate;
    public GetPlatformsNameByID(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> processPlatforms(List<Integer> platformsIds, HttpHeaders headers) {
        List<String> platformsNames = new ArrayList<>();

        if (platformsIds != null && !platformsIds.isEmpty()) {
            String platformsBody = "fields name; where id = (" +
                    String.join(",", platformsIds.stream().map(String::valueOf).toArray(String[]::new)) +
                    ");";

            HttpEntity<String> requestEntity = new HttpEntity<>(platformsBody, headers);

            try {
                ResponseEntity<List<PlatformsInfo>> platformsResponse = restTemplate.exchange(
                        PLATFORMS_URL, HttpMethod.POST, requestEntity,
                        new ParameterizedTypeReference<List<PlatformsInfo>>() {}
                );

                if (platformsResponse.getBody() != null) {
                    for (PlatformsInfo platform : platformsResponse.getBody()) {
                        platformsNames.add(platform.name());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error fetching platforms: " + e.getMessage());
            }
        }

        return platformsNames;
    }
}
