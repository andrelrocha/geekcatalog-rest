package rocha.andre.api.domain.gameList.customRepository;

import rocha.andre.api.domain.utils.sheet.GamesOnUserListInfoDTO;

import java.util.List;
import java.util.UUID;

public interface GameListNativeSqlRepository {
    List<Object[]> findAllGamesInfoByUserId(UUID userId);
}
