package rocha.andre.api.domain.utils.API.IGDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import rocha.andre.api.domain.utils.API.IGDB.DTO.*;
import rocha.andre.api.domain.utils.API.IGDB.utils.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GetGameInfoOnIGDB {
    @Autowired
    private FormatGameName formatGameName;
    @Autowired
    private GetCompanyDetails getCompanyDetails;
    @Autowired
    private GetGameInfoByName getGameInfoByName;
    @Autowired
    private GetGenresNameByID getGenresNameByID;
    @Autowired
    private GetInvolvedCompaniesByID getInvolvedCompaniesByID;
    @Autowired
    private GetPlatformsNameByID getPlatformsNameByID;
    @Autowired
    private GetReleaseDatesByID getReleaseDatesByID;

    private final ExecutorService executorService;
    private static final int MAX_REQUESTS_PER_SECOND = 4;
    private static final int MAX_OPEN_REQUESTS = 8;
    private static final long REQUEST_DELAY_MS = 1000 / MAX_REQUESTS_PER_SECOND;

    public GetGameInfoOnIGDB() {
        this.executorService = Executors.newFixedThreadPool(MAX_OPEN_REQUESTS);
    }

    public IGDBResponseFullInfoDTO fetchGameDetails(IGDBQueryInfoDTO queryInfo) {
        var gameName = formatGameName.formatGameName(queryInfo.gameName());
        var clientId = queryInfo.clientId();
        var token = queryInfo.token();

        try {
            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("Client-ID", clientId);
            headers.set("Accept", "application/json");

            var gameResponse = getGameInfoByName.fetchGameDetailsFromIGDB(gameName, headers);

            if (gameResponse.getBody() != null && !gameResponse.getBody().isEmpty()) {
                GameInfo gameInfo = gameResponse.getBody().get(0);

                var gameNameResponse = gameInfo.name();

                int yearOfRelease = getReleaseDatesByID.processReleaseDatesList(gameInfo.releaseDates(), headers);
                List<String> genreNames = getGenresNameByID.processGenres(gameInfo.genres(), headers);
                List<String> platformsNames = getPlatformsNameByID.processPlatforms(gameInfo.platforms(), headers);
                List<InvolvedCompanyInfo> involvedCompanies = getInvolvedCompaniesByID.processInvolvedCompanies(gameInfo.involvedCompanies(), headers);
                List<CompanyReturnDTO> companyDetails = getCompanyDetails.processCompanyDetails(involvedCompanies, headers);

                return new IGDBResponseFullInfoDTO(gameNameResponse, yearOfRelease, genreNames, platformsNames, companyDetails);
            }

        } catch (HttpClientErrorException e) {
            System.err.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }

        return null;
    }
}