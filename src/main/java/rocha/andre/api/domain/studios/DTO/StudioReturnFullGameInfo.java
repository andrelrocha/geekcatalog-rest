package rocha.andre.api.domain.studios.DTO;

import rocha.andre.api.domain.studios.Studio;

import java.util.UUID;

public record StudioReturnFullGameInfo(UUID id, String name) {
    public StudioReturnFullGameInfo(Studio studio) {
        this(studio.getId(), studio.getName());
    }
}
