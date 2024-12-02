package rocha.andre.api.domain.utils.fullGame.utils.create.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.utils.API.IGDB.utils.GenreNameFormatterFromIGDB;
import rocha.andre.api.domain.utils.fullGame.utils.create.processor.GenreProcessor;
import rocha.andre.api.service.GameGenreService;
import rocha.andre.api.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;

@Service
public class GenreProcessorImpl implements GenreProcessor {
    @Autowired
    private GenreNameFormatterFromIGDB genreNameFormatterFromIGDB;
    @Autowired
    private GenreService genreService;
    @Autowired
    private GameGenreService gameGenreService;
    private static final Logger logger = LoggerFactory.getLogger(GenreProcessorImpl.class);

    @Override
    public void addGameGenre(List<GenreReturnDTO> newGameGenres, String gameId, GenreReturnDTO genre) {
        var gameGenreDTO = new GameGenreDTO(gameId, genre.id().toString());
        var gameGenreCreated = gameGenreService.createGameGenre(gameGenreDTO);
        newGameGenres.add(new GenreReturnDTO(gameGenreCreated.genreId(), gameGenreCreated.genreName()));
    }

    @Override
    public Map<String, GenreReturnDTO> fetchGenresWithId(List<String> normalizedGenres) {
        ArrayList<GenreDTO> genreDTOs = (ArrayList<GenreDTO>) normalizedGenres.stream()
                .map(GenreDTO::new)
                .collect(Collectors.toList());

        return genreService.getGenresByName(genreDTOs).stream()
                .collect(Collectors.toMap(
                        genre -> genre.name().toLowerCase().trim(),
                        genre -> genre
                ));
    }

    @Override
    public GenreReturnDTO handleGenreCreationOrFetch(String genreName, GenreReturnDTO genre, String gameId) {
        if (genre == null) {
            logger.info("Criando novo gênero '{}'", capitalizeEachWord(genreName));
            genre = genreService.createGenre(new GenreDTO(capitalizeEachWord(genreName)));
        } else {
            logger.info("Gênero '{}' já existe. Associando ao jogo ID: {}", genre.name(), gameId);
        }
        return genre;
    }

    @Override
    public List<String> normalizeAndConvertNames(List<String> genres) {
        return genreNameFormatterFromIGDB.normalizeAndConvertNames(genres);
    }
}
