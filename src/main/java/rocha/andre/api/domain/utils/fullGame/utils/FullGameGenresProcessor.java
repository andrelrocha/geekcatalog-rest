package rocha.andre.api.domain.utils.fullGame.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.infra.utils.stringFormatter.StringFormatter;
import rocha.andre.api.service.GameGenreService;
import rocha.andre.api.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class FullGameGenresProcessor {
    @Autowired
    private GameGenreService gameGenreService;
    @Autowired
    private GenreService genreService;

    private static final Logger logger = LoggerFactory.getLogger(FullGameGenresProcessor.class);

    public ArrayList<GenreReturnDTO> processFullGameGenres(List<String> genres, String gameId) {
        var newGameGenres = new ArrayList<GenreReturnDTO>();

        List <String> genresWithSystemNomenclature = convertGenres(genres);
        List<String> normalizedGenres = genresWithSystemNomenclature.stream()
                .map(StringFormatter::normalizeString)
                .toList();

        Map<String, GenreReturnDTO> normalizedGenresWithId = genreService.getGenresByName(
                new ArrayList<>(normalizedGenres.stream()
                        .map(GenreDTO::new)
                        .toList())
        ).stream().collect(Collectors.toMap(
                genre -> genre.name().toLowerCase().trim(),
                genre -> genre
        ));

        for (String genreName : normalizedGenres) {
            GenreReturnDTO genre = normalizedGenresWithId.getOrDefault(genreName, null);

            if (genre == null) {
                logger.info("Criando novo gênero '{}'", capitalizeEachWord(genreName));
                genre = genreService.createGenre(new GenreDTO(capitalizeEachWord(genreName)));
            } else {
                logger.info("Gênero '{}' já existe. Associando ao jogo ID: {}", genre.name(), gameId);
            }

            var gameGenreDTO = new GameGenreDTO(gameId, genre.id().toString());
            var gameGenreCreated = gameGenreService.createGameGenre(gameGenreDTO);

            newGameGenres.add(new GenreReturnDTO(gameGenreCreated.genreId(), gameGenreCreated.genreName()));
        }

        return newGameGenres;
    }

    private List<String> convertGenres(List<String> genres) {
        List<String> normalizedGenres = new ArrayList<>();

        for (String genre : genres) {
            var normalizedGenre = normalizeString(genre);

            if (normalizedGenre.startsWith("real time strategy")) {
                normalizedGenres.add("Real Time Strategy");
            } else if (normalizedGenre.startsWith("role-playing")) {
                normalizedGenres.add("RPG");
            } else if (normalizedGenre.startsWith("turn-based strategy")) {
                normalizedGenres.add("Turn-based Strategy");
            } else if (normalizedGenre.startsWith("hack and slash/beat 'em up")) {
                normalizedGenres.add("Hack and Slash");
                normalizedGenres.add("Beat em Up");
            } else {
                if (normalizedGenre.contains("(")) {
                    normalizedGenre = normalizedGenre.replaceAll("\\s*\\(.*?\\)", "").trim();
                }
                normalizedGenres.add(normalizedGenre);
            }
        }

        return normalizedGenres;
    }
}
