package rocha.andre.api.domain.game.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameConsole.GameConsoleRepository;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameStudio.GameStudioRepository;
import rocha.andre.api.domain.imageGame.ImageGameRepository;
import rocha.andre.api.domain.imageGame.useCase.DeleteImageGame;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteGame {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameGenreRepository gameGenreRepository;
    @Autowired
    private GameConsoleRepository gameConsoleRepository;
    @Autowired
    private GameStudioRepository gameStudioRepository;
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private DeleteImageGame deleteImageGame;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public void deleteGame(String gameId) {
        var gameIdUUID = UUID.fromString(gameId);
        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado no processo de delete do jogo."));

        transactionTemplate.execute(status -> {
            try {
                var gameListToDelete = gameListRepository.findAllByGameId(game.getId());
                gameListRepository.deleteAll(gameListToDelete);
                var gameGenresToDelete = gameGenreRepository.findAllByGameId(game.getId());
                gameGenreRepository.deleteAll(gameGenresToDelete);
                var gameConsolesToDelete = gameConsoleRepository.findAllByGameId(game.getId());
                gameConsoleRepository.deleteAll(gameConsolesToDelete);
                var gameStudiosToDelete = gameStudioRepository.findAllByGameId(game.getId());
                gameStudioRepository.deleteAll(gameStudiosToDelete);
                deleteImageGame.deleteImageGameByGameId(gameId);
                gameRepository.delete(game);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Ocorreu um erro na transação de delete do jogo e de suas entidades relacionadas", e);
            }
            return null;
        });
    }
}
