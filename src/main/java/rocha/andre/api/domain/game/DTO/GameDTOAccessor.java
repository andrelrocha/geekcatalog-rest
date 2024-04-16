package rocha.andre.api.domain.game.DTO;

import java.util.Optional;
import java.util.UUID;

public class GameDTOAccessor {
    private final GameReturnDTO gameReturnDTO;

    public GameDTOAccessor(GameReturnDTO gameReturnDTO) {
        this.gameReturnDTO = gameReturnDTO;
    }

    public UUID getId() {
        return gameReturnDTO.id();
    }

    public String getName() {
        return gameReturnDTO.name();
    }

    public int getMetacritic() {
        return gameReturnDTO.metacritic();
    }

    public int getYearOfRelease() {
        return gameReturnDTO.yearOfRelease();
    }

    public int getYearOfReleaseWithValidation() {
        return Optional.ofNullable(gameReturnDTO.yearOfRelease()).orElse(0);
    }

    public int getMetacriticWithValidation() {
        return Optional.ofNullable(gameReturnDTO.metacritic()).orElse(0);
    }
}
