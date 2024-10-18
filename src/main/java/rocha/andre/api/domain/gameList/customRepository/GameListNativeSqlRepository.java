package rocha.andre.api.domain.gameList.customRepository;

import java.util.List;
import java.util.UUID;

public interface GameListNativeSqlRepository {
    List<Object[]> findAllGamesInfoByUserId(UUID userId);
}
