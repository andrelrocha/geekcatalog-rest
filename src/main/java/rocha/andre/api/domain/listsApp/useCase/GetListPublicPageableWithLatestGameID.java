package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnWithGameIdsDTO;
import rocha.andre.api.domain.listsApp.ListAppRepository;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class GetListPublicPageableWithLatestGameID {
    @Autowired
    private ListAppRepository repository;
    @Autowired
    private GameListRepository gameListRepository;

    public Page<ListAppReturnWithGameIdsDTO> getAllPublicListsByUserIdWithLatestGamesID(String userId, Pageable pageable) {
        var userIdUUID = UUID.fromString(userId);

        var pageablePublicListsByUserId = repository.findAllListsPublicByUserId(pageable, userIdUUID).map(ListAppReturnDTO::new);

        var pageWithLatestGames = pageablePublicListsByUserId.map(listApp -> {
            var latestGamesOnListID = gameListRepository.findTop4ByListIdOrderByCreatedAtDesc(listApp.id());
            return new ListAppReturnWithGameIdsDTO(listApp, new ArrayList<>(latestGamesOnListID));
        });

        return pageWithLatestGames;
    }
}
