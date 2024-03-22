package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicDTO;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicReturnDTO;
import rocha.andre.api.domain.profilePic.useCase.AddProfilePic;
import rocha.andre.api.domain.profilePic.useCase.ReturnProfilePic;
import rocha.andre.api.service.ProfilePicService;

import java.io.IOException;
import java.util.UUID;

@Service
public class ProfilePicServiceImpl implements ProfilePicService {
    @Autowired
    private AddProfilePic addProfilePic;
    @Autowired
    private ReturnProfilePic returnProfilePic;

    @Override
    public ProfilePicReturnDTO addProfilePic(ProfilePicDTO dto) throws IOException {
        var profilePic = addProfilePic.addProfilePic(dto);
        return profilePic;
    }

    @Override
    public byte[] returnProfilePic(UUID userId) throws Exception {
        var profilePic = returnProfilePic.returnProfilePic(userId);
        return profilePic;
    }
}
