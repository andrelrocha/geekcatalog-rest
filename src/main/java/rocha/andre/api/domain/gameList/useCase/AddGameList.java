package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.consoles.useCase.GetAllConsolesByGameId;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameList.DTO.*;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationStrategy;
import rocha.andre.api.domain.gameList.strategy.OwnerPermissionValidation;
import rocha.andre.api.domain.gameList.strategy.ParticipantPermissionValidation;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class AddGameList {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private ConsoleRepository consoleRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetAllConsolesByGameId getAllConsolesByGameId;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;
    @Autowired
    private ParticipantPermissionValidation participantPermissionValidation;

    public GameListFullReturnDTO addGameList(GameListDTO data) {
        var userIdUUID = UUID.fromString(data.userId());
        var user = userRepository.findById(userIdUUID)
                .orElseThrow(() -> new ValidationException("No user was found with the provided id when adding the game list."));

        var listIdUUID = UUID.fromString(data.listId());
        var list = listAppRepository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("No list was found with the provided id when adding the game list."));

        PermissionValidationStrategy strategy = user.getId().equals(list.getUser().getId()) ?
                new OwnerPermissionValidation() :
                participantPermissionValidation;

        strategy.validate(user, list, userIdUUID);

        var gameIdUUID = UUID.fromString(data.gameId());
        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(()-> new ValidationException("No game was found with the provided id when adding the game list."));

        var gameAlreadyExists = gameListRepository.existsByGameIdAndListId(game.getId(), list.getId());
        if (gameAlreadyExists) {
            throw new ValidationException("This game has already been added to the list.");
        }

        var consoleIdUUID = UUID.fromString(data.consoleId());
        var console = consoleRepository.findById(consoleIdUUID)
                .orElseThrow(() -> new ValidationException("No console was found with the provided id when adding the game list."));

        var gameConsoles = getAllConsolesByGameId.getAllConsolesByGameId(null, data.gameId());
        var consoleIds = gameConsoles.getContent().stream().map(ConsoleReturnDTO::id).toList();

        if (!consoleIds.contains(console.getId())) {
            throw new ValidationException("The selected console is not associated with the game.");
        }

        var gameListCreateDTO = new GameListCreateDTO(user, game, list, console, data.note());
        var gameList = new GameList(gameListCreateDTO);

        var gameListOnDB = gameListRepository.save(gameList);

        return new GameListFullReturnDTO(gameListOnDB);
    }
}
