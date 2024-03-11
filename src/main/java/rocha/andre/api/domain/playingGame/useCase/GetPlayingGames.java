package rocha.andre.api.domain.playingGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameReturnDTO;
import rocha.andre.api.domain.playingGame.PlayingGameRepository;
import org.springframework.data.domain.Pageable;

@Component
public class GetPlayingGames {
    @Autowired
    private PlayingGameRepository repository;

    public Page<PlayingGameReturnDTO> getAllPlayingGames(Pageable pageable) {
        return repository.findAllPlayingGames(pageable).map(PlayingGameReturnDTO::new);
    }

}
