package rocha.andre.api.domain.profilePic.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.profilePic.ProfilePicRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.utils.imageCompress.ImageUtils;

import java.util.UUID;

@Component
public class ReturnProfilePic {
    @Autowired
    private ProfilePicRepository repository;

    public byte[] returnProfilePic(UUID userId) throws Exception {
        var profilePic = repository.findProfilePicByUserId(userId);

        if (profilePic == null) {
            throw new ValidationException("Não foi encontrada imagem para o id de usuário informado");
        }

        var compressedImage = profilePic.getImage();
        var decompressedImageData = ImageUtils.decompressImage(compressedImage);

        return decompressedImageData;
    }

}
