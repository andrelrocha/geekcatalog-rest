package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.user.User;

public record UserReturnDTO(Long id,
                            String login) {

    public UserReturnDTO(User user) {
        this(user.getId(), user.getLogin());
    }
}
