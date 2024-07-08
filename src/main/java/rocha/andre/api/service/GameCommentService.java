package rocha.andre.api.service;

import rocha.andre.api.domain.gameComment.DTO.CreateGameCommentDTO;
import rocha.andre.api.domain.gameComment.DTO.GameCommentReturnDTO;

public interface GameCommentService {
    GameCommentReturnDTO addGameComment(CreateGameCommentDTO data, String tokenJWT);

}
