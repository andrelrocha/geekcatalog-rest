package rocha.andre.api.domain.profilePic.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicDTO;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicReturnDTO;
import rocha.andre.api.domain.profilePic.ProfilePic;
import rocha.andre.api.domain.profilePic.ProfilePicRepository;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.utils.imageCompress.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
public class AddProfilePic {
    @Autowired
    private ProfilePicRepository profilePicRepository;
    @Autowired
    private UserRepository userRepository;

    public ProfilePicReturnDTO addProfilePic(ProfilePicDTO dto) throws IOException {
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado usuário com o id informado."));

        var existsByUserId = profilePicRepository.existsByUserId(user.getId());

        if (existsByUserId) {
            var profilePicUpdated = updateImage(dto);
            return new ProfilePicReturnDTO(profilePicUpdated);
        }

        var compressedImage = compressImage(dto.imageFile());

        var profilePic = new ProfilePic(compressedImage, user);

        var profilePicOnDB = profilePicRepository.save(profilePic);

        //AJEITAR O RETORNO PARA SÓ VOLTAR O USER.NAME
        return new ProfilePicReturnDTO(profilePicOnDB);
    }

    private ProfilePic updateImage(ProfilePicDTO dto) throws IOException {
        var profilePic = profilePicRepository.findProfilePicByUserId(dto.userId());

        var compressedImageBytes = compressImage(dto.imageFile());

        profilePic.updateImage(compressedImageBytes);

        var imageOnDB = profilePicRepository.save(profilePic);

        return imageOnDB;
    }

    public byte[] compressImage(MultipartFile imageFile) throws IOException {
        validateImageFormat(imageFile);

        byte[] compressedImageBytes = ImageUtils.compressImage(imageFile.getBytes());

        if (compressedImageBytes == null) {
            throw new ValidationException("Erro ao comprimir a imagem. O resultado do método compressImage é nulo.");
        }

        return compressedImageBytes;
    }

    private void validateImageFormat(MultipartFile imageFile) throws ValidationException {
        try {
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            if (image == null) {
                throw new ValidationException("A imagem não pôde ser lida ou não é um formato de imagem suportado.");
            }

            if (!"jpg".equalsIgnoreCase(imageFile.getContentType().substring(imageFile.getContentType().lastIndexOf("/") + 1)) && !"jpeg".equalsIgnoreCase(imageFile.getContentType().substring(imageFile.getContentType().lastIndexOf("/") + 1))) {
                throw new ValidationException("O formato da imagem deve ser JPG ou JPEG.");
            }
        } catch (IOException e) {
            throw new ValidationException("Erro ao validar o formato da imagem.");
        }
    }
}
