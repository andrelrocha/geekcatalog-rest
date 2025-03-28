package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.DeleteGameListDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationFactory;
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
    private PermissionValidationFactory permissionValidationFactory;

    public void deleteGameList(DeleteGameListDTO data) {
        UUID gameListId = UUID.fromString(data.gameListId());
        var gameList = gameListRepository.findById(gameListId)
                .orElseThrow(() -> new ValidationException("No game in a list was found for the provided ID."));

        ListApp list = listAppRepository.findById(gameList.getList().getId())
                .orElseThrow(() -> new ValidationException("No list was found for the provided ID."));

        var user = getUserByTokenJWT.getUserByID(data.tokenJWT());

        var validationStrategy = permissionValidationFactory.getStrategy(user, list, PermissionEnum.DELETE_GAME);
        validationStrategy.validate(user, list);

        gameListRepository.deleteById(gameList.getId());
    }
}
