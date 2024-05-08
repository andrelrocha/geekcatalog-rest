package rocha.andre.api.domain.gameGenre.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameGenre.DTO.CreateGameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.GameGenre;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.genres.GenreRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class CreateGameGenre {
    @Autowired
    private GameGenreRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    public GameGenreReturnDTO createGameGenre(GameGenreDTO data) {
        var gameIdUUID = UUID.fromString(data.gameId());
        var genreIdUUID = UUID.fromString(data.genreId());

        var entityAlreadyCreated = repository.existsByGameIdAndGenreId(gameIdUUID, genreIdUUID);

        if (entityAlreadyCreated) {
            throw new ValidationException("Já existe registro com o game id e o genre id informado");
        }

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado ao tentar criar gamegenre"));

        var genre = genreRepository.findById(genreIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado genre com o id informado ao tentar criar gamegenre"));

        var createDTO = new CreateGameGenreDTO(game, genre);

        var gameGenre = new GameGenre(createDTO);

        var gameGenreOnDB = repository.save(gameGenre);

        return new GameGenreReturnDTO(gameGenreOnDB);
    }

}
