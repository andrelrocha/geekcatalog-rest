package rocha.andre.api.domain.listsApp.DTO;

import rocha.andre.api.domain.listsApp.ListApp;

import java.util.ArrayList;
import java.util.UUID;

public record ListAppReturnWithGameIdsDTO(UUID id, String name, String description, UUID ownerId, String userName, ArrayList<UUID> latestGamesOnListID) {
    public ListAppReturnWithGameIdsDTO(ListAppReturnDTO listAppReturnDTO, ArrayList<UUID> gamesOnListID) {
        this(listAppReturnDTO.id(), listAppReturnDTO.name(), listAppReturnDTO.description(), listAppReturnDTO.ownerId(), listAppReturnDTO.userName(), gamesOnListID);
    }
}
