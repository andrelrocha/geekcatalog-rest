package rocha.andre.api.domain.imageGame.DTO;

import rocha.andre.api.domain.imageGame.ImageGame;

import java.util.UUID;

public record ImageGameReturnDTO(UUID gameId, String gameName, String imageUrl) {
    public ImageGameReturnDTO(ImageGame imageGame) {
        this(imageGame.getGame().getId(), imageGame.getGame().getName(), imageGame.getImageUrl());
    }
}
