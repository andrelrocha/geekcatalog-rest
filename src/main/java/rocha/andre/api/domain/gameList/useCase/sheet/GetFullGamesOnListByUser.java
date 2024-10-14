package rocha.andre.api.domain.gameList.useCase.sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.GameListRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GetFullGamesOnListByUser {
    @Autowired
    private GameListRepository gameListRepository;

    public List<GamesOnUserListInfoDTO> getAllGamesByUserId(String userId) {
        var userIdUUID = UUID.fromString(userId);

        return gameListRepository.findAllGamesInfoByUserId(userIdUUID);
    }
}
