package rocha.andre.api.domain.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameConsole.GameConsoleRepository;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.gameStudio.GameStudioRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Service
public class GetFullGameAdminInfoService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameStudioRepository gameStudioRepository;
    @Autowired
    private GameGenreRepository gameGenreRepository;
    @Autowired
    private GameConsoleRepository gameConsoleRepository;

    public FullGameReturnDTO getFullGameInfoAdmin(String gameId) {
        var gameIdUUID = UUID.fromString(gameId);

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado id de jogo para o id informado no full game service"));

        var consoleInfoList = gameConsoleRepository.findAllConsolesInfoByGameId(game.getId());
        var genreInfoList = gameGenreRepository.findAllGenresInfoByGameId(game.getId());
        var studioInfoList = gameStudioRepository.findAllStudioByGameId(game.getId());

        return new FullGameReturnDTO(game, consoleInfoList, genreInfoList, studioInfoList);
    }
}
