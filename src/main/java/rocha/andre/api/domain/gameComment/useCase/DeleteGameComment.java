package rocha.andre.api.domain.gameComment.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameComment.GameCommentRepository;
import rocha.andre.api.domain.user.UseCase.GetUserIdByJWT;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteGameComment {
    @Autowired
    private GameCommentRepository gameCommentRepository;
    @Autowired
    private GetUserIdByJWT getUserIdByJWT;
    @Autowired
    private UserRepository userRepository;

    public void deleteGameComment(String tokenJWT, String commentId) {
        var user = getUserIdByJWT.getUserByJWT(tokenJWT);
        if (user == null) {
            throw new RuntimeException("Não foi encontrado usuário no processo de deletar um game comment.");
        }

        var commentIdUUID = UUID.fromString(commentId);
        var comment = gameCommentRepository.findById(commentIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado comentário com o id informado."));

        if (!comment.getUser().getId().equals(UUID.fromString(user.userId()))) {
            throw new BadCredentialsException("O usuário que está tentando apagar o comentário não é o criador do mesmo.");
        }

        gameCommentRepository.deleteById(comment.getId());
    }
}
