package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;

import java.util.ArrayList;
import java.util.List;

public interface GenreService {
    GenreReturnDTO createGenre(GenreDTO data);
    Page<GenreReturnDTO> getAllGenres(Pageable pageable);
    List<GenreReturnDTO> getGenresByName(ArrayList<GenreDTO> data);
}
