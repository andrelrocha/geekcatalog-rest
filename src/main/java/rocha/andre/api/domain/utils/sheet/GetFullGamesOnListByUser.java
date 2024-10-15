package rocha.andre.api.domain.utils.sheet;

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

        return gameListRepository.findAllGamesInfoByUserId(userIdUUID)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private GamesOnUserListInfoDTO mapToDTO(Object[] row) {
        return new GamesOnUserListInfoDTO(
                (String) row[0],       // name
                (Integer) row[1],      // metacritic
                (Integer) row[2],      // yearOfRelease
                (String) row[3],       // genres
                (String) row[4],       // studios
                (String) row[5],       // consolePlayed
                (Integer) row[6],      // rating
                (String) row[7],       // note
                (UUID) row[8]          // id
        );
    }
}
