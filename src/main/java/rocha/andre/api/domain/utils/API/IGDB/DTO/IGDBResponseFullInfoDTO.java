package rocha.andre.api.domain.utils.API.IGDB.DTO;

import java.util.List;

public record IGDBResponseFullInfoDTO(String gameName, int yearOfRelease, List<String> genresName, List<String> platformsName, List<CompanyReturnDTO> involvedCompanies) {
}
