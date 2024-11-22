package rocha.andre.api.domain.genres.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.genres.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetGenresIdByName {
    @Autowired
    private GenreRepository genreRepository;

    public List<GenreReturnDTO> getGenresByName(ArrayList<GenreDTO> data) {
        List<String> names = data.stream()
                .map(GenreDTO::name)
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();
        var genres = genreRepository.findAllByNamesIgnoreCaseAndTrimmed(names);

        return genres.stream()
                .map(genre -> new GenreReturnDTO(genre.getId(), genre.getName()))
                .toList();
    }
}
