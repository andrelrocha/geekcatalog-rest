package rocha.andre.api.domain.gameComment.DTO;

import rocha.andre.api.domain.gameComment.GameComment;

import java.util.UUID;

public record GameCommentReturnDTO(UUID id, UUID userId, UUID gameId, String comment) {
    public GameCommentReturnDTO(GameComment gameComment) {
        this(gameComment.getId(), gameComment.getUser().getId(), gameComment.getGame().getId(), gameComment.getComment());
    }
}
