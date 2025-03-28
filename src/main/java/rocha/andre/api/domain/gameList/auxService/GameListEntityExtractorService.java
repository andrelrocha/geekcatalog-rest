package rocha.andre.api.domain.gameList.auxService;

import rocha.andre.api.domain.consoles.Console;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.User;

public interface GameListEntityExtractorService {
    User extractUser(String userId);
    ListApp extractList(String listId);
    Game extractGame(String gameId);
    GameList extractGameList(String gameListId);
    Console extractConsole(String consoleId, String gameId);
}
