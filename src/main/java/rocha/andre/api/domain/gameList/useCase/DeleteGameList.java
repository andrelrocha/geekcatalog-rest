package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.DeleteGameListDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteGameList {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;

    public void deleteGameList(DeleteGameListDTO data) {
        var gameListUUID = UUID.fromString(data.gameListId());
        var gameList = gameListRepository.findById(gameListUUID)
                .orElseThrow(()-> new ValidationException("Não foi encontrado jogo em uma lista com o id informado na deleção de game list"));

        var list = listAppRepository.findById(gameList.getList().getId())
                .orElseThrow(()-> new ValidationException("Não foi encontrada lista com o id informado na deleção de game list"));

        var user = getUserByTokenJWT.getUserByID(data.tokenJWT());
        var userIdUUID = UUID.fromString(user.id());

        var errorMessagePermission = "O usuário que está tentando remover jogos não é o dono da lista ou não tem permissão para tanto";
        if (!userIdUUID.equals(list.getUser().getId())) {
            var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userIdUUID, list.getId());
            if (!listsPermission.isEmpty()) {
                var addGameEnum = PermissionEnum.DELETE_GAME;
                var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(addGameEnum);
                var userPermissionList = listPermissionUserRepository.findByParticipantIdAndListIdAndPermissionId(userIdUUID, list.getId(), permission.id());

                if (userPermissionList == null) {
                    throw new ValidationException(errorMessagePermission);
                }
                if (!list.getUser().getId().equals(userPermissionList.getOwner().getId())) {
                    throw new ValidationException(errorMessagePermission);
                }
            } else {
                throw new ValidationException(errorMessagePermission);
            }
        }

        gameListRepository.deleteById(gameList.getId());
    }
}
