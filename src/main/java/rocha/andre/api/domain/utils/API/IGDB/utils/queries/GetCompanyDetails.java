package rocha.andre.api.domain.utils.API.IGDB.utils.queries;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CountryInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.InvolvedCompanyInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetCompanyDetails {
    private static final String COMPANIES_URL = "https://api.igdb.com/v4/companies";
    private static final String COUNTRY_URL = "https://restcountries.com/v3.1/alpha/";
    private final RestTemplate restTemplate;
    public GetCompanyDetails(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CompanyReturnDTO> processCompanyDetails(List<InvolvedCompanyInfo> involvedCompanies, HttpHeaders headers) {
        List<CompanyReturnDTO> companyReturnList = new ArrayList<>();

        if (!involvedCompanies.isEmpty()) {
            List<Integer> companyIds = involvedCompanies.stream()
                    .map(InvolvedCompanyInfo::company)
                    .toList();

            var companyDetailBody = "fields name, country; where id = (" +
                    String.join(",", companyIds.stream().map(String::valueOf).toArray(String[]::new)) +
                    ");";

            try {
                ResponseEntity<List<CompanyInfo>> companyDetailResponse = restTemplate.exchange(
                        COMPANIES_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(companyDetailBody, headers),
                        new ParameterizedTypeReference<List<CompanyInfo>>() {}
                );

                if (companyDetailResponse.getBody() != null) {
                    for (CompanyInfo companyInfo : companyDetailResponse.getBody()) {
                        CountryInfo countryInfoResponse;

                        try {
                            String countryCode = String.valueOf(companyInfo.country());
                            String countryUrl = COUNTRY_URL + countryCode;

                            ResponseEntity<List<CountryInfo>> countryResponse = restTemplate.exchange(
                                    countryUrl,
                                    HttpMethod.GET,
                                    null,
                                    new ParameterizedTypeReference<List<CountryInfo>>() {}
                            );

                            if (countryResponse.getBody() != null && !countryResponse.getBody().isEmpty()) {
                                countryInfoResponse = countryResponse.getBody().get(0);
                            } else {
                                countryInfoResponse = new CountryInfo(new CountryInfo.Name("N/A"), "N/A");
                            }
                        } catch (Exception e) {
                            // Mapeia como "N/A" se a chamada ao país lançar uma exceção (ex: status != 200)
                            countryInfoResponse = new CountryInfo(new CountryInfo.Name("N/A"), "N/A");
                        }

                        InvolvedCompanyInfo involvedCompanyInfo = involvedCompanies.stream()
                                .filter(info -> info.company() == companyInfo.id())
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Involved company info not found for company ID: " + companyInfo.id()));

                        CompanyReturnDTO companyReturnDTO = new CompanyReturnDTO(
                                companyInfo.name(),
                                involvedCompanyInfo.developer(),
                                involvedCompanyInfo.publisher(),
                                countryInfoResponse
                        );

                        companyReturnList.add(companyReturnDTO);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error fetching company details: " + e.getMessage());
                throw e;
            }
        } else {
            System.out.println("No company details found.");

        }
        return companyReturnList;
    }
}
