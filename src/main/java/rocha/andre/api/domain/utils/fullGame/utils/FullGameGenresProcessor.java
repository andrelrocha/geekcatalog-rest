package rocha.andre.api.domain.utils.fullGame.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.utils.fullGame.utils.processor.GenreProcessor;

import java.util.ArrayList;
import java.util.List;

@Component
public class FullGameGenresProcessor {
    @Autowired
    private GenreProcessor genreProcessor;

    public ArrayList<GenreReturnDTO> processFullGameGenres(List<String> genres, String gameId) {
        var normalizedGenresWithSystemNomenclature = genreProcessor.normalizeAndConvertNames(genres);

        var genresMapWithId = genreProcessor.fetchGenresWithId(normalizedGenresWithSystemNomenclature);

        var newGameGenres = new ArrayList<GenreReturnDTO>();

        for (String genreName : normalizedGenresWithSystemNomenclature) {
            GenreReturnDTO genre = genresMapWithId.getOrDefault(genreName, null);

            genre = genreProcessor.handleGenreCreationOrFetch(genreName, genre, gameId);

            genreProcessor.addGameGenre(newGameGenres, gameId, genre);
        }

        return newGameGenres;
    }
}
