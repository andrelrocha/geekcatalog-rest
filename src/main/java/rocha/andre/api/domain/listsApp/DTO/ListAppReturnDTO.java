package rocha.andre.api.domain.listsApp.DTO;

import rocha.andre.api.domain.listsApp.ListApp;

import java.util.UUID;

public record ListAppReturnDTO(UUID id, String name, String description, UUID userId, String userName) {
    public ListAppReturnDTO(ListApp listApp) {
        this(listApp.getId(), listApp.getName(), listApp.getDescription(), listApp.getUser().getId(), listApp.getUser().getName());
    }
}
