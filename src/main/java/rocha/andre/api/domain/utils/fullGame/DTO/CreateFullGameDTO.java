package rocha.andre.api.domain.utils.fullGame.DTO;

import java.util.List;

public record CreateFullGameDTO(String name, int metacritic, int yearOfRelease, List<String> consoles, List<String> genres, List<String> studios) {
}