package rocha.andre.api.domain.utils.API.IGDB;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import rocha.andre.api.domain.utils.API.IGDB.DTO.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GetGenresAndStudioByGameName {

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private static final int MAX_REQUESTS_PER_SECOND = 4;
    private static final int MAX_OPEN_REQUESTS = 8;
    private static final long REQUEST_DELAY_MS = 1000 / MAX_REQUESTS_PER_SECOND;
    private static final String GAMES_URL = "https://api.igdb.com/v4/games";
    private static final String GENRES_URL = "https://api.igdb.com/v4/genres";
    private static final String COMPANIES_URL = "https://api.igdb.com/v4/companies";
    private static final String INVOLVED_COMPANIES_URL = "https://api.igdb.com/v4/involved_companies";
    private static final String COUNTRY_URL = "https://restcountries.com/v3.1/alpha/";

    public GetGenresAndStudioByGameName(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.executorService = Executors.newFixedThreadPool(MAX_OPEN_REQUESTS);
    }

    public IGDBResponseFullInfoDTO fetchGameDetails(IGDBQueryInfoDTO queryInfo) {
        var gameName = formatGameName(queryInfo.gameName());
        var clientId = queryInfo.clientId();
        var token = queryInfo.token();

        try {
            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("Client-ID", clientId);
            headers.set("Accept", "application/json");

            var body = "fields name, genres, involved_companies; " +
                    "where name = \"" + gameName + "\"; " +
                    "limit 1; " +
                    "sort aggregated_rating desc;";

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<List<GameInfo>> gameResponse = restTemplate.exchange(
                    GAMES_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<List<GameInfo>>() {}
            );

            if (gameResponse.getBody() != null && !gameResponse.getBody().isEmpty()) {
                GameInfo gameInfo = gameResponse.getBody().get(0);

                var gameNameResponse = gameInfo.name();

                List<String> genreNames = processGenres(gameInfo.genres(), headers);
                List<InvolvedCompanyInfo> involvedCompanies = processInvolvedCompanies(gameInfo.involvedCompanies(), headers);
                List<CompanyReturnDTO> companyDetails = processCompanyDetails(involvedCompanies, headers);

                return new IGDBResponseFullInfoDTO(gameNameResponse, genreNames, companyDetails);
            }

        } catch (HttpClientErrorException e) {
            System.err.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }

        return null;
    }

    private String formatGameName(String gameName) {
        String[] words = gameName.split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        var lowerCaseWords = new String[]{"the", "of", "and", "in", "on", "at", "for", "with", "a", "an"};

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();

            if (i == 0 || !isLowerCaseWord(word, lowerCaseWords)) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            } else {
                formattedName.append(word);
            }

            if (i < words.length - 1) {
                formattedName.append(" ");
            }
        }

        return formattedName.toString();
    }

    private boolean isLowerCaseWord(String word, String[] lowerCaseWords) {
        for (String lowerWord : lowerCaseWords) {
            if (word.equals(lowerWord)) {
                return true;
            }
        }
        return false;
    }

    private List<String> processGenres(List<Integer> genreIds, HttpHeaders headers) {
        List<String> genreNames = new ArrayList<>();
        if (genreIds != null && !genreIds.isEmpty()) {
            String genreBody = "fields name; where id = (" + String.join(",", genreIds.stream().map(String::valueOf).toArray(String[]::new)) + ");";

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

    private List<InvolvedCompanyInfo> processInvolvedCompanies(List<Integer> companyIds, HttpHeaders headers) {
        List<InvolvedCompanyInfo> involvedCompanies = new ArrayList<>();

        if (companyIds != null && !companyIds.isEmpty()) {
            var companyBody = "fields company, publisher, developer; " +
                    "where id = (" +
                    String.join(",", companyIds.stream().map(String::valueOf).toArray(String[]::new)) +
                    ");";

            HttpEntity<String> requestEntity = new HttpEntity<>(companyBody, headers);

            ResponseEntity<List<InvolvedCompanyInfo>> companyResponse = restTemplate.exchange(
                    INVOLVED_COMPANIES_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<List<InvolvedCompanyInfo>>() {}
            );

            if (companyResponse.getBody() != null) {
                involvedCompanies.addAll(companyResponse.getBody());
            }
        }
        return involvedCompanies;
    }

    private List<CompanyReturnDTO> processCompanyDetails(List<InvolvedCompanyInfo> involvedCompanies, HttpHeaders headers) {
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
                        var countryCode = String.valueOf(companyInfo.country());
                        var countryUrl = COUNTRY_URL + countryCode;

                        ResponseEntity<List<CountryInfo>> countryResponse = restTemplate.exchange(
                                countryUrl,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<CountryInfo>>() {}
                        );

                        if (countryResponse.getBody() != null && !countryResponse.getBody().isEmpty()) {
                            CountryInfo countryInfoResponse = countryResponse.getBody().get(0);

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