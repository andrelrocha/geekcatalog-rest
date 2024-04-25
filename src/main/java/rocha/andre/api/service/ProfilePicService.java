package rocha.andre.api.service;

import rocha.andre.api.domain.profilePic.DTO.ProfilePicDTO;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicReturnDTO;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicUserIdDTO;

import java.io.IOException;
import java.util.UUID;

public interface ProfilePicService {
    ProfilePicReturnDTO addProfilePic(ProfilePicDTO dto) throws IOException;
    byte[] returnProfilePic(UUID userId) throws Exception;
    public void deleteProfilePic(UUID userID);
}
