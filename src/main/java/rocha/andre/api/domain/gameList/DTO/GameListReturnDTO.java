package rocha.andre.api.domain.gameList.DTO;

import rocha.andre.api.domain.gameList.GameList;

import java.util.UUID;

public record GameListReturnDTO(UUID id, UUID userId, UUID gameId, String gameName, UUID listId, UUID consoleId) {
    public GameListReturnDTO(GameList gameList) {
        this(gameList.getId(), gameList.getUser().getId(), gameList.getGame().getId(), gameList.getGame().getName(), gameList.getList().getId(), gameList.getConsole().getId());
    }
}
