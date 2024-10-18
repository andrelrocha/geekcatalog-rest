package rocha.andre.api.domain.utils.fullList.DTO;

import java.util.ArrayList;
import java.util.UUID;

public record FullListReturnDTO(UUID id, String name, String description, UUID ownerId, int count, ArrayList<String> gamesUri) {
}
