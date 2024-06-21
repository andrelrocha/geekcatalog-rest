package rocha.andre.api.domain.genres.DTO;

import java.util.UUID;

public record GenreCountDTO(UUID genreId, String genreName, int count) {
}
