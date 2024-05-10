package rocha.andre.api.domain.gameStudio.DTO;

import rocha.andre.api.domain.gameStudio.GameStudio;

import java.util.UUID;

public record GameStudioReturnDTO(UUID id, String gameName, String studioName) {
    public GameStudioReturnDTO(GameStudio gameStudio) {
        this(gameStudio.getId(), gameStudio.getGame().getName(), gameStudio.getStudio().getName());
    }
}
