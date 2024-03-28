package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.user.User;

public record UserReturnDTO(String id,
                            String login,
                            String name,
                            String cpf) {

    public UserReturnDTO(User user) {
        this(user.getId().toString(), user.getLogin(), user.getName(), user.getCpf());
    }
}
