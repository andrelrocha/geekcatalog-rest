package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameComment.DTO.CreateGameCommentDTO;
import rocha.andre.api.domain.gameComment.DTO.GameCommentJOINReturnDTO;
import rocha.andre.api.domain.gameComment.DTO.GameCommentReturnDTO;

public interface GameCommentService {
    GameCommentReturnDTO addGameComment(CreateGameCommentDTO data, String tokenJWT);
    Page<GameCommentJOINReturnDTO> getCommentsPageable(Pageable pageable, String gameId);
    void deleteGameComment(String tokenJWT, String commentId);
}
