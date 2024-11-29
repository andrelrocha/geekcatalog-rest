package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.genres.useCase.CreateGenre;
import rocha.andre.api.domain.genres.useCase.GetAllGenres;
import rocha.andre.api.domain.genres.useCase.GetGenresIdByName;
import rocha.andre.api.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private CreateGenre createGenre;
    @Autowired
    private GetAllGenres getAllGenres;
    @Autowired
    private GetGenresIdByName getGenresIdByName;

    @Override
    public GenreReturnDTO createGenre(GenreDTO data) {
        return createGenre.createGenre(data);
    }

    @Override
    public Page<GenreReturnDTO> getAllGenres(Pageable pageable) {
        var genres = getAllGenres.getAllGenres(pageable);
        return genres;
    }

    @Override
    public List<GenreReturnDTO> getGenresByName(ArrayList<GenreDTO> data) {
        return getGenresIdByName.getGenresByName(data);
    }
}
