package rocha.andre.api.domain.fullGame_mobile.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.fullGame_mobile.DTO.FullGameUserDTO;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameConsole.GameConsoleRepository;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.gameStudio.GameStudioRepository;
import rocha.andre.api.domain.imageGame.useCase.GetImageGameByGameID;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Service
public class GetFullGameInfoService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameStudioRepository gameStudioRepository;
    @Autowired
    private GameGenreRepository gameGenreRepository;
    @Autowired
    private GameConsoleRepository gameConsoleRepository;
    @Autowired
    private GetImageGameByGameID getImageGameByGameID;

    public FullGameUserDTO getFullGameInfo(String gameId) {
        var gameIdUUID = UUID.fromString(gameId);

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado id de jogo para o id informado no full game service"));

        var gameStudio = gameStudioRepository.findAllStudioNamesByGameId(game.getId());

        var gameGenre = gameGenreRepository.findAllGenresNamesByGameId(game.getId());

        var gameConsole = gameConsoleRepository.findAllConsolesNamesByGameId(game.getId());

        var gameImageUrl = getImageGameByGameID.getImageGamesByGameID(gameId).imageUrl();

        return new FullGameUserDTO(game, gameStudio, gameGenre, gameConsole, gameImageUrl);
    }
}