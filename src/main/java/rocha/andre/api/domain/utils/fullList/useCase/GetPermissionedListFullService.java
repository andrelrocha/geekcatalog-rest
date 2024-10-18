package rocha.andre.api.domain.utils.fullList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.utils.fullList.DTO.FullListReturnDTO;
import rocha.andre.api.domain.gameList.useCase.CountGameListByListID;
import rocha.andre.api.domain.gameList.useCase.GetLatestGameListByListID;
import rocha.andre.api.domain.imageGame.useCase.GetImageGameByGameID;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.PermissionRepository;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class GetPermissionedListFullService {
    @Autowired
    private ListAppRepository repository;
    @Autowired
    private CountGameListByListID countGameListByListID;
    @Autowired
    private GetLatestGameListByListID getLatestGameListByListID;
    @Autowired
    private GetImageGameByGameID getImageGameByGameID;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;

    public Page<FullListReturnDTO> getPermissionedListsByUserId(String userId, Pageable pageable) {
        var userIdUUID = UUID.fromString(userId);

        try {
            var readEnum = (PermissionEnum.READ).toString();
            var readPermission = permissionRepository.findByPermissionName(readEnum);
            var listsPermissionWithReadPermissionToUser = listPermissionUserRepository.findAllByParticipantIdAndPermissionId(userIdUUID, readPermission.getId());
            var listsAppID = new ArrayList<UUID>();
            for (ListPermissionUser listPermission : listsPermissionWithReadPermissionToUser) {
                listsAppID.add(listPermission.getList().getId());
            }

            var pageableListsByUserId = repository.findAllListsAppById(listsAppID, pageable).map(list -> {
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
        } catch (Exception e) {
            System.err.println("Erro ao obter listas com permissão de leitura: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
