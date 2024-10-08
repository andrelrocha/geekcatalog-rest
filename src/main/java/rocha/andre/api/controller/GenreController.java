package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.service.GenreService;

@RestController
@RequestMapping("/genres")
@Tag(name = "Genre Routes Mapped on Controller")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping("/all")
    public ResponseEntity<Page<GenreReturnDTO>> getAllGenres(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "" + Integer.MAX_VALUE) int size,
                                                               @RequestParam(defaultValue = "name") String sortField,
                                                               @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var genresPageable = genreService.getAllGenres(pageable);
        return ResponseEntity.ok(genresPageable);
    }
}
