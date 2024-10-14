package rocha.andre.api.domain.gameList.customRepository;

import rocha.andre.api.domain.gameList.useCase.sheet.GamesOnUserListInfoDTO;

import java.util.List;
import java.util.UUID;

public interface GameListNativeSqlRepository {
    List<GamesOnUserListInfoDTO> findAllGamesInfoByUserId(UUID userId);
}
