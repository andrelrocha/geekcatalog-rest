package rocha.andre.api.domain.fullList_mobile.DTO;

import java.util.ArrayList;
import java.util.UUID;

public record FullListReturnDTO(UUID id, String name, String description, UUID ownerId, int count, ArrayList<String> gamesUri) {
}
