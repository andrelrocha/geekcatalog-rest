package rocha.andre.api.domain.gameComment.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameComment.DTO.GameCommentJOINReturnDTO;
import rocha.andre.api.domain.gameComment.GameCommentRepository;

import java.util.UUID;

@Component
public class GetGameComments {
    @Autowired
    public GameCommentRepository gameCommentRepository;

    public Page<GameCommentJOINReturnDTO> getCommentsPageable(Pageable pageable, String gameId) {
        var gameIdUUID = UUID.fromString(gameId);
        var gameComments = gameCommentRepository.getAllComentsByGameId(gameIdUUID, pageable);

        return gameComments;
    }
}
