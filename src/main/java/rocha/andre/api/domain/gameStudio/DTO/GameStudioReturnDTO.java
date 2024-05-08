package rocha.andre.api.domain.gameStudio.DTO;

import rocha.andre.api.domain.gameStudio.GameStudio;

public record GameStudioReturnDTO(String gameName, String studioName) {
    public GameStudioReturnDTO(GameStudio gameStudio) {
        this(gameStudio.getGame().getName(), gameStudio.getStudio().getName());
    }
}
