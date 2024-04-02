package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.user.User;

import java.time.LocalDate;

public record UserReturnDTO(String id,
                            String login,
                            String name,
                            String cpf,
                            String phone,
                            LocalDate birthday) {

    public UserReturnDTO(User user) {
        this(user.getId().toString(), user.getLogin(), user.getName(), user.getCpf(), user.getPhone(), user.getBirthday());
    }
}
