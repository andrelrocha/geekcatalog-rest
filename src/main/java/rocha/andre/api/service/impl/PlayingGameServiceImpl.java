package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameReturnDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameUpdateDTO;
import rocha.andre.api.domain.playingGame.useCase.AddPlayingGame;
import rocha.andre.api.domain.playingGame.useCase.DeletePlayingGame;
import rocha.andre.api.domain.playingGame.useCase.GetPlayingGames;
import rocha.andre.api.domain.playingGame.useCase.UpdatePlayingGameUseCase;
import rocha.andre.api.service.PlayingGameService;

@Component
public class PlayingGameServiceImpl implements PlayingGameService {
    @Autowired
    private AddPlayingGame addPlayingGame;
    @Autowired
    private GetPlayingGames getPlayingGames;
    @Autowired
    private DeletePlayingGame deletePlayingGame;
    @Autowired
    private UpdatePlayingGameUseCase updatePlayingGameUseCase;

    @Override
    public PlayingGameReturnDTO addPlayingGame(PlayingGameDTO data) {
        var playingGame = addPlayingGame.addPlayingGame(data);
        return playingGame;
    }

    @Override
    public Page<PlayingGameReturnDTO> getAllPlayingGames(Pageable pageable) {
        var playingGames = getPlayingGames.getAllPlayingGames(pageable);
        return playingGames;
    }

    @Override
    public void deletePlayingGame(long id) {
        deletePlayingGame.deletePlayingGame(id);
    }

    @Override
    public PlayingGameReturnDTO updatePlayingGame(PlayingGameUpdateDTO data) {
        var gameUpdated = updatePlayingGameUseCase.updatePlayingGame(data);
        return gameUpdated;
    }
}
