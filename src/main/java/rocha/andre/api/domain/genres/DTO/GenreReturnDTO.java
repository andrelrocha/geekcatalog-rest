package rocha.andre.api.domain.genres.DTO;

import rocha.andre.api.domain.genres.Genre;

import java.util.UUID;

public record GenreReturnDTO(UUID id, String name) {
    public GenreReturnDTO(Genre genre) {
        this(genre.getId(), genre.getName());
    }
}
