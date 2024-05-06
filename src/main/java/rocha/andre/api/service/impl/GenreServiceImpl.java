package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.genres.useCase.GetAllGenres;
import rocha.andre.api.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GetAllGenres getAllGenres;

    @Override
    public Page<GenreReturnDTO> getAllGenres(Pageable pageable) {
        var genres = getAllGenres.getAllGenres(pageable);
        return genres;
    }
}
