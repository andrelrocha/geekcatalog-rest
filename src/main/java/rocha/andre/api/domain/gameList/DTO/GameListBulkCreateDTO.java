package rocha.andre.api.domain.gameList.DTO;

import java.util.ArrayList;

public record GameListBulkCreateDTO(String userId, ArrayList<String> gamesId, String listId) {
}
