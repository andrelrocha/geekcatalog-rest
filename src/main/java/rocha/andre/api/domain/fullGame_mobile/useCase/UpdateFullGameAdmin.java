package rocha.andre.api.domain.fullGame_mobile.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.fullGame_mobile.DTO.FullGameUpdateDTO;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.useCase.UpdateGame;
import rocha.andre.api.domain.gameGenre.DTO.UpdateGameGenreDTO;
import rocha.andre.api.domain.gameGenre.useCase.UpdateGameGenres;

@Service
public class UpdateFullGameAdmin {
    @Autowired
    private UpdateGame updateGameEntity;
    @Autowired
    private UpdateGameGenres updateGameGenres;

    public void updateFullGameInfos(FullGameUpdateDTO data, String gameId) {
        var updateGameDTO = new GameDTO(data.name(), data.metacritic(), data.yearOfRelease());

        var updatedGame = updateGameEntity.updateGame(updateGameDTO, gameId);

        var gameGenres = data.genres();

        //var gameGenresDTO = new UpdateGameGenreDTO(gameGenres);

        //var updatedGameGenres = updateGameGenres.updateGameGenres(gameGenresDTO, gameId);
    }
}
