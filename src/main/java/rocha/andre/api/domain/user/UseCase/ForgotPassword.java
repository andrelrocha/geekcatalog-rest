package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.domain.user.DTO.UserForgotDTO;
import rocha.andre.api.domain.user.DTO.UserOnlyLoginDTO;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.utils.mail.*;

import java.time.LocalDateTime;

@Component
public class ForgotPassword {
    @Autowired
    private UserRepository repository;
    @Autowired
    private GenerateTokenForgetPassword mailToken;
    @Autowired
    private MailSenderMime mailSender;

    public void forgotPassword(UserOnlyLoginDTO data) {
        var login = data.login();
        var userExists = repository.existsByLogin(login);

        if (!userExists) {
            throw new ValidationException("No user was found for the provided login");
        }

        var token = mailToken.generateEmailToken();
        var inOneHour = LocalDateTime.now().plusHours(1);
        var forgotDTO = new UserForgotDTO(token, inOneHour);

        var user = repository.findByLoginToHandle(login);
        user.forgotPassword(forgotDTO);

        var subject = "Forgot Password - Geek Catalog";

        var mailDTO = new MailDTO(subject, login, token);

        mailSender.sendMail(mailDTO);
    }
}