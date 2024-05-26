package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetGameListByID {
    @Autowired
    private GameListRepository repository;

    public GameListFullReturnDTO getGameListByID(String gameListID) {
        var gameListIdUUID = UUID.fromString(gameListID);

        var gameList = repository.findById(gameListIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado um jogo em uma lista com o id informado"));

        return new GameListFullReturnDTO(gameList);
    }
}
