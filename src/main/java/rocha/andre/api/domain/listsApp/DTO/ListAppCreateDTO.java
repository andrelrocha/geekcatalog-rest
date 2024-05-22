package rocha.andre.api.domain.listsApp.DTO;

import rocha.andre.api.domain.user.User;

public record ListAppCreateDTO(String name, String description, User user) {
}
