package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameComment.DTO.CreateGameCommentDTO;
import rocha.andre.api.domain.gameComment.DTO.GameCommentReturnDTO;
import rocha.andre.api.domain.gameComment.useCase.AddGameComment;
import rocha.andre.api.service.GameCommentService;

@Service
public class GameCommentServiceImpl implements GameCommentService {
    @Autowired
    private AddGameComment addGameComment;

    @Override
    public GameCommentReturnDTO addGameComment(CreateGameCommentDTO data, String tokenJWT) {
        return addGameComment.addGameComment(data, tokenJWT);
    }
}
