package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameReturnDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameUpdateDTO;

public interface PlayingGameService {
    PlayingGameReturnDTO addPlayingGame(PlayingGameDTO data);
    Page<PlayingGameReturnDTO> getAllPlayingGames(Pageable pageable);
    void deletePlayingGame(long id);

    PlayingGameReturnDTO updatePlayingGame(PlayingGameUpdateDTO data);
}
