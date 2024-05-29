package rocha.andre.api.domain.fullList_mobile.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.fullList_mobile.DTO.FullListReturnDTO;
import rocha.andre.api.domain.gameList.useCase.CountGameListByListID;
import rocha.andre.api.domain.gameList.useCase.GetLatestGameListByListID;
import rocha.andre.api.domain.imageGame.useCase.GetImageGameByGameID;
import rocha.andre.api.domain.listsApp.ListAppRepository;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class GetListFullInfoService {
    @Autowired
    private ListAppRepository repository;
    @Autowired
    private CountGameListByListID countGameListByListID;
    @Autowired
    private GetLatestGameListByListID getLatestGameListByListID;
    @Autowired
    private GetImageGameByGameID getImageGameByGameID;

    public Page<FullListReturnDTO> getAllListsByUserId(String userId, Pageable pageable) {
        var userIdUUID = UUID.fromString(userId);

        var pageableListsByUserId = repository.findAllListsByUserId(pageable, userIdUUID).map(list -> {
            var listIdString = (list.getId()).toString();
            var gameCount = countGameListByListID.countGamesByListID(listIdString);

            var pageableGameList = PageRequest.of(0, 4);
            var latestGameList = getLatestGameListByListID.getLatestGamesByListID(pageableGameList, listIdString);

            var gamesUri = new ArrayList<String>();

            latestGameList.forEach(game -> {
                var gameUri = getImageGameByGameID.getImageGamesByGameID((game.gameId()).toString());
                gamesUri.add(gameUri.imageUrl());
            });

            return new FullListReturnDTO(
                    list.getId(),
                    list.getName(),
                    list.getDescription(),
                    list.getUser().getId(),
                    gameCount.gameCount(),
                    gamesUri
            );
        });

        return pageableListsByUserId;
    }
}
