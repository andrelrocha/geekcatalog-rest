package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndUserDTO;
import rocha.andre.api.domain.gameRating.useCase.GetRatingByGameAndUser;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetGameListByID {
    @Autowired
    private GameListRepository repository;
    @Autowired
    private GetRatingByGameAndUser getRatingByGameAndUser;

    public GameListFullReturnDTO getGameListByID(String gameListId) {
        var gameListIdUUID = UUID.fromString(gameListId);

        var gameList = repository.findById(gameListIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado um jogo em uma lista com o id informado"));

        var gameRatingDTO = new GameRatingByGameAndUserDTO(gameList.getGame().getId(), gameList.getUser().getId());
        var userRating = getRatingByGameAndUser.getRatingByGameAndUser(gameRatingDTO);

        return new GameListFullReturnDTO(gameList, userRating.rating());
    }
}
