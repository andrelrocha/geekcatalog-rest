package rocha.andre.api.domain.utils.fullGame.utils.create.processor;

import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;

import java.util.List;
import java.util.Map;

public interface GenreProcessor {
    void addGameGenre(List<GenreReturnDTO> newGameGenres, String gameId, GenreReturnDTO genre);
    Map<String, GenreReturnDTO> fetchGenresWithId(List<String> normalizedGenres);
    GenreReturnDTO handleGenreCreationOrFetch(String genreName, GenreReturnDTO genre, String gameId);
    List<String> normalizeAndConvertNames(List<String> genres);
}
