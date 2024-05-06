package rocha.andre.api.domain.genres.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.genres.GenreRepository;

@Component
public class GetAllGenres {
    @Autowired
    private GenreRepository repository;

    public Page<GenreReturnDTO> getAllGenres(Pageable pageable) {
        var genres = repository.findGenresOrderedByName(pageable).map(GenreReturnDTO::new);
        return genres;
    }

}
