package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;

public interface GenreService {
    Page<GenreReturnDTO> getAllGenres(Pageable pageable);
}
