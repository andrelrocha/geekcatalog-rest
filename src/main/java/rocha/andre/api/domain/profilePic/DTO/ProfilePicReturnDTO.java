package rocha.andre.api.domain.profilePic.DTO;

import rocha.andre.api.domain.profilePic.ProfilePic;
import rocha.andre.api.domain.user.User;

import java.util.UUID;

public record ProfilePicReturnDTO(UUID id, String login) {
    public ProfilePicReturnDTO(ProfilePic profilePic) {
        this(profilePic.getUser().getId(), profilePic.getUser().getLogin());
    }
}
