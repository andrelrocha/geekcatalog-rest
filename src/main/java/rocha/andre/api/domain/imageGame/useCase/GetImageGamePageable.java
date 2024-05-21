package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.ImageGameRepository;


@Component
public class GetImageGamePageable {
    @Autowired
    private ImageGameRepository repository;

    public Page<ImageGameReturnDTO> getImageGames(Pageable pageable) {
        var imageGames = repository.findAllImageGames(pageable).map(ImageGameReturnDTO::new);
        return imageGames;
    }
}
