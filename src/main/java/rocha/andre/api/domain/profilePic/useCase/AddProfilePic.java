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
import java.util.Objects;

@Component
public class AddProfilePic {
    @Autowired
    private ProfilePicRepository profilePicRepository;
    @Autowired
    private UserRepository userRepository;

    public ProfilePicReturnDTO addProfilePic(ProfilePicDTO dto) throws IOException {
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ValidationException("No user was found for the provided ID."));

        var existsByUserId = profilePicRepository.existsByUserId(user.getId());

        if (existsByUserId) {
            var profilePicUpdated = updateImage(dto);
            return new ProfilePicReturnDTO(profilePicUpdated);
        }

        var compressedImage = compressImage(dto.imageFile());

        var profilePic = new ProfilePic(compressedImage, user);

        var profilePicOnDB = profilePicRepository.save(profilePic);

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

        if (compressedImageBytes.length == 0) {
            throw new ValidationException("The compressed image is empty.");
        }

        return compressedImageBytes;
    }

    private void validateImageFormat(MultipartFile imageFile) throws ValidationException {
        try {
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            if (image == null) {
                throw new ValidationException("The image could not be read or is not a supported image format.");
            }

            if (!"jpg".equalsIgnoreCase(Objects.requireNonNull(imageFile.getContentType()).substring(imageFile.getContentType().lastIndexOf("/") + 1)) && !"jpeg".equalsIgnoreCase(imageFile.getContentType().substring(imageFile.getContentType().lastIndexOf("/") + 1))) {
                throw new ValidationException("The image format must be JPG or JPEG.");
            }
        } catch (IOException e) {
            throw new ValidationException("Error validating the image format.");
        }
    }
}
