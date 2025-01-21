package rocha.andre.api.domain.genres.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.genres.Genre;
import rocha.andre.api.domain.genres.GenreRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class CreateGenre {
    @Autowired
    private GenreRepository genreRepository;

    public GenreReturnDTO createGenre(GenreDTO data) {
        var genreAlreadyOnDB = genreRepository.findByName(data.name());

        if (genreAlreadyOnDB != null) {
            throw new ValidationException("A genre with the provided name already exists");
        }

        var newGenre = new Genre(data.name());
        var genreOnDB = genreRepository.save(newGenre);

        return new GenreReturnDTO(genreOnDB);
    }

}
