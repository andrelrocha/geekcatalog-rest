package rocha.andre.api.domain.utils.fullGame.DTO;

import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;

import java.util.ArrayList;
import java.util.UUID;

public record FullGameReturnDTO(UUID id, String name, Integer metacritic, Integer yearOfRelease, ArrayList<ConsoleReturnDTO> consoles, ArrayList<GenreReturnDTO> genres, ArrayList<StudioReturnFullGameInfo> studios) {
    public FullGameReturnDTO(Game game, ArrayList<ConsoleReturnDTO> consoles, ArrayList<GenreReturnDTO> genres, ArrayList<StudioReturnFullGameInfo> studios) {
        this(game.getId(), game.getName(), game.getMetacritic(), game.getYearOfRelease(), consoles, genres, studios);
    }

    public FullGameReturnDTO(GameReturnDTO gameData, ArrayList<ConsoleReturnDTO> consoles, ArrayList<GenreReturnDTO> genres, ArrayList<StudioReturnFullGameInfo> studios) {
        this(gameData.id(), gameData.name(), gameData.metacritic(), gameData.yearOfRelease(), consoles, genres, studios);
    }
}
