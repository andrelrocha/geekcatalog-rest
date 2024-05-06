package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.ImageGame;
import rocha.andre.api.domain.imageGame.ImageGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;

@Component
public class ReturnAllImageGamesPageable {

    @Autowired
    private ImageGameRepository repository;

    public Page<ImageGameReturnDTO> returnAllImages(Pageable pageable) {
        Page<ImageGame> imageGames = repository.findAll(pageable);

        if (imageGames.isEmpty()) {
            throw new ValidationException("Não foi encontrada nenhuma imagem para os jogos no sistema.");
        }

        ArrayList<ImageGameReturnDTO> dtos = new ArrayList<>();
        imageGames.forEach(imageGame -> {
            ImageGameReturnDTO dto = new ImageGameReturnDTO(imageGame.getGame().getId(), imageGame.getImage());
            dtos.add(dto);
        });

        return new PageImpl<>(dtos, pageable, imageGames.getTotalElements());
    }
}
