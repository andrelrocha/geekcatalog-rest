package rocha.andre.api.domain.utils.API.IGDB.utils.queries;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.InvolvedCompanyInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetInvolvedCompaniesByID {
    private static final String INVOLVED_COMPANIES_URL = "https://api.igdb.com/v4/involved_companies";
    private final RestTemplate restTemplate;

    public GetInvolvedCompaniesByID(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<InvolvedCompanyInfo> processInvolvedCompanies(List<Integer> companyIds, HttpHeaders headers) {
        List<InvolvedCompanyInfo> involvedCompanies = new ArrayList<>();

        if (companyIds != null && !companyIds.isEmpty()) {
            var companyBody = "fields company, publisher, developer; " +
                    "where id = (" +
                    String.join(",", companyIds.stream().map(String::valueOf).toArray(String[]::new)) +
                    ");";

            HttpEntity<String> requestEntity = new HttpEntity<>(companyBody, headers);

            try {
                ResponseEntity<List<InvolvedCompanyInfo>> companyResponse = restTemplate.exchange(
                        INVOLVED_COMPANIES_URL, HttpMethod.POST, requestEntity,
                        new ParameterizedTypeReference<List<InvolvedCompanyInfo>>() {}
                );

                if (companyResponse.getBody() != null) {
                    involvedCompanies.addAll(companyResponse.getBody());
                }

            } catch (HttpClientErrorException e) {
                System.err.println("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            } catch (RestClientException e) {
                System.err.println("Client Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        return involvedCompanies;
    }
}
