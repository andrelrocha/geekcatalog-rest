package rocha.andre.api.domain.gameComment.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameComment.DTO.CreateGameCommentDTO;
import rocha.andre.api.domain.gameComment.DTO.GameCommentDTO;
import rocha.andre.api.domain.gameComment.DTO.GameCommentReturnDTO;
import rocha.andre.api.domain.gameComment.GameComment;
import rocha.andre.api.domain.gameComment.GameCommentRepository;
import rocha.andre.api.domain.user.UseCase.GetUserIdByJWT;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class AddGameComment {
    @Autowired
    private GameCommentRepository gameCommentRepository;
    @Autowired
    private GetUserIdByJWT getUserIdByJWT;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    public GameCommentReturnDTO addGameComment(CreateGameCommentDTO data, String tokenJWT) {
        var user = getUserIdByJWT.getUserByJWT(tokenJWT);
        if (user == null) {
            throw new RuntimeException("Não foi encontrado usuário no processo de add game comment.");
        }

        var userEntity = userRepository.findByIdToHandle(UUID.fromString(user.userId()));

        var commentExists = gameCommentRepository.gameCommentExists(userEntity.getId(), UUID.fromString(data.gameId()), data.comment());

        if (commentExists) {
            throw new ValidationException("O usuário já fez esse mesmo comentário neste jogo.");
        }

        var game = gameRepository.findById(UUID.fromString(data.gameId()))
                .orElseThrow(() -> new RuntimeException("Não foi encontrado jogo com o id informado"));

        var dto = new GameCommentDTO(userEntity, game, data.comment());

        var gameComment = new GameComment(dto);

        var gameCommentOnDB = gameCommentRepository.save(gameComment);

        return new GameCommentReturnDTO(gameCommentOnDB);
    }

}
