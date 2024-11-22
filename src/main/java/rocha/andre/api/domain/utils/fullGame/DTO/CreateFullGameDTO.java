package rocha.andre.api.domain.utils.fullGame.DTO;

import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;

import java.util.List;

public record CreateFullGameDTO(String name, int metacritic, int yearOfRelease, List<String> consoles, List<String> genres, List<CompanyReturnDTO> studios) {
}