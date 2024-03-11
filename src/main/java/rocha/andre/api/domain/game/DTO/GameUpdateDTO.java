package rocha.andre.api.domain.game.DTO;

public record GameUpdateDTO(Long id, String name, int length, int metacritic, int excitement, String genre) {
}
