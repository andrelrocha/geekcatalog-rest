package rocha.andre.api.domain.gameGenre.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameGenre.DTO.*;
import rocha.andre.api.domain.gameGenre.GameGenre;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.genres.GenreRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

@Component
public class UpdateGameGenres {
    @Autowired
    private GameGenreRepository gameGenreRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GetAllGameGenreByGameID getAllGameGenreByGameID;

    public Page<GameGenreReturnDTO> updateGameGenres(UpdateGameGenreDTO data, String gameId) {
        var gameIdUUID = UUID.fromString(gameId);
        var pageable = PageRequest.of(0, 50);

        var genres = gameGenreRepository.findAllGenreIdsByGameId(gameIdUUID);

        var genresIdDataUUID = new ArrayList<UUID>();
        for (String genreId : data.genres()) {
            var genreIdUUID = UUID.fromString(genreId);
            genresIdDataUUID.add(genreIdUUID);
        }

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado id do jogo no update game genre"));

        if (new HashSet<>(genres).containsAll(genresIdDataUUID) && new HashSet<>(genresIdDataUUID).containsAll(genres)) {
            return null;
        } else {
            if (!new HashSet<>(genres).containsAll(genresIdDataUUID)) {
                for (String genreId : data.genres()) {
                    var genreIdUUID = UUID.fromString(genreId);
                    if (genres.contains(genreIdUUID)) {
                        continue;
                    }
                    var genre = genreRepository.findById(genreIdUUID)
                            .orElseThrow(() -> new ValidationException("Não foi encontrado id do genre no update game genre"));

                    var gameGenreDTO = new CreateGameGenreDTO(game, genre);
                    var gameGenre = new GameGenre(gameGenreDTO);
                    gameGenreRepository.save(gameGenre);
                }
            }
            if (!new HashSet<>(genresIdDataUUID).containsAll(genres)) {
                gameGenreRepository.deleteGenresByGameIdAndGenreIds(gameIdUUID,genresIdDataUUID);
            }
        }

        return getAllGameGenreByGameID.getAllGameGenresByGameId(gameId, pageable);
    }
}
