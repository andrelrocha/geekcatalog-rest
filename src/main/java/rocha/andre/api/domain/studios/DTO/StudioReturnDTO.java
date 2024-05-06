package rocha.andre.api.domain.studios.DTO;

import rocha.andre.api.domain.studios.Studio;

import java.util.UUID;

public record StudioReturnDTO(UUID id, String name, String countryName) {

    public StudioReturnDTO(Studio studio) {
        this(studio.getId(), studio.getName(), studio.getCountry().getName());
    }
}
