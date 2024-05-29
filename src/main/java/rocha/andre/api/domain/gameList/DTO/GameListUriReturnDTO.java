package rocha.andre.api.domain.gameList.DTO;

import rocha.andre.api.domain.gameList.GameList;

import java.util.UUID;

public record GameListUriReturnDTO(UUID id, UUID userId, UUID gameId, String gameName, UUID consoleId, String consolePlayed, String uri) {
    public GameListUriReturnDTO(GameList gameList, String uri) {
        this(gameList.getId(), gameList.getUser().getId(), gameList.getGame().getId(), gameList.getGame().getName(),
                gameList.getConsole() != null ? gameList.getConsole().getId() : null,  gameList.getConsole() != null ? gameList.getConsole().getName() : null, uri);
    }
}
