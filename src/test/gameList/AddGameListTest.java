/*
package rocha.andre.api.domain.gameList.useCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.consoles.useCase.GetAllConsolesByGameId;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameList.DTO.GameListDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddGameListTest {

    @InjectMocks
    private AddGameList addGameList;

    @Mock
    private GameListRepository gameListRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private ListAppRepository listAppRepository;
    @Mock
    private ConsoleRepository consoleRepository;
    @Mock
    private ListPermissionUserRepository listPermissionUserRepository;
    @Mock
    private GetAllConsolesByGameId getAllConsolesByGameId;
    @Mock
    private GetPermissionByNameENUM getPermissionByNameENUM;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddGameListSuccessfully() {
        // Arrange
        var userId = UUID.randomUUID();
        var listId = UUID.randomUUID();
        var gameId = UUID.randomUUID();
        var consoleId = UUID.randomUUID();

        var data = new GameListDTO(userId.toString(), listId.toString(), gameId.toString(), consoleId.toString(), "Note");

        var user = mockUser(userId);
        var list = mockListApp(listId, user);
        var game = mockGame(gameId);
        var console = mockConsole(consoleId);
        var consolesReturn = mockConsolesForGame(gameId, consoleId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(listAppRepository.findById(listId)).thenReturn(Optional.of(list));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(consoleRepository.findById(consoleId)).thenReturn(Optional.of(console));
        when(gameListRepository.existsByGameIdAndListId(gameId, listId)).thenReturn(false);
        when(getAllConsolesByGameId.getAllConsolesByGameId(null, gameId.toString())).thenReturn(consolesReturn);

        // Act
        var result = addGameList.addGameList(data);

        // Assert
        assertNotNull(result);
        verify(gameListRepository).save(any());
    }

    @Test
    void shouldThrowValidationExceptionWhenUserNotFound() {
        // Arrange
        var data = new GameListDTO("invalid-user", "list-id", "game-id", "console-id", "Note");

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidationException.class, () -> addGameList.addGameList(data));
    }

    @Test
    void shouldThrowValidationExceptionWhenGameAlreadyExistsInList() {
        // Arrange
        var userId = UUID.randomUUID();
        var listId = UUID.randomUUID();
        var gameId = UUID.randomUUID();
        var consoleId = UUID.randomUUID();

        var data = new GameListDTO(userId.toString(), listId.toString(), gameId.toString(), consoleId.toString(), "Note");

        var user = mockUser(userId);
        var list = mockListApp(listId, user);
        var game = mockGame(gameId);
        var console = mockConsole(consoleId);
        var consolesReturn = mockConsolesForGame(gameId, consoleId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(listAppRepository.findById(listId)).thenReturn(Optional.of(list));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(consoleRepository.findById(consoleId)).thenReturn(Optional.of(console));
        when(gameListRepository.existsByGameIdAndListId(gameId, listId)).thenReturn(true);

        // Act & Assert
        assertThrows(ValidationException.class, () -> addGameList.addGameList(data));
    }

    // Mock helpers
    private User mockUser(UUID userId) {
        var user = mock(User.class);
        when(user.getId()).thenReturn(userId);
        return user;
    }

    private ListApp mockListApp(UUID listId, User user) {
        var list = mock(ListApp.class);
        when(list.getId()).thenReturn(listId);
        when(list.getUser()).thenReturn(user);
        return list;
    }

    private Game mockGame(UUID gameId) {
        var game = mock(Game.class);
        when(game.getId()).thenReturn(gameId);
        return game;
    }

    private Console mockConsole(UUID consoleId) {
        var console = mock(Console.class);
        when(console.getId()).thenReturn(consoleId);
        return console;
    }

    private PageImpl<GameConsoleReturnDTO> mockConsolesForGame(UUID gameId, UUID consoleId) {
        var consoleReturnDTO = mock(ConsoleReturnDTO.class);
        when(consoleReturnDTO.id()).thenReturn(consoleId);
        return new PageImpl<>(List.of(consoleReturnDTO));
    }
}
*/