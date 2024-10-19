package rocha.andre.api.domain.utils.API.IGDB.DTO;

import java.util.List;

public record IGDBResponseFullInfoDTO(String gameName, List<String> genresName, List<CompanyReturnDTO> involvedCompanies) {
}
