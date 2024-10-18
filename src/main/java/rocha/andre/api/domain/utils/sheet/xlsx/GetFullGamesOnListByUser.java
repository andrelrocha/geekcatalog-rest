package rocha.andre.api.domain.utils.sheet.xlsx;

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
                (Integer) row[1],      // yearOfRelease
                (String) row[2],       // genres
                (String) row[3],       // studios
                (String) row[4],       // consolePlayed
                (Integer) row[5],      // rating
                (UUID) row[6],         // gameListId
                (UUID) row[7],         // gameId
                (String) row[8]        // note
        );
    }
}
