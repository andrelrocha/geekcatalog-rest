package rocha.andre.api.domain.gameComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.gameComment.DTO.GameCommentJOINReturnDTO;

import java.util.UUID;

public interface GameCommentRepository extends JpaRepository<GameComment, UUID> {
    @Query("""
            SELECT CASE WHEN COUNT(gc) > 0 THEN TRUE ELSE FALSE END
            FROM GameComment gc
            WHERE gc.comment = :comment AND gc.user.id = :userId AND gc.game.id = :gameId
            """)
    boolean gameCommentExists(UUID userId, UUID gameId, String comment);

    @Query("""
            SELECT new rocha.andre.api.domain.gameComment.DTO.GameCommentJOINReturnDTO(
                            gc.id, u.id, u.name,
                            COALESCE(gr.rating, 0) AS rating,
                            gc.game.id, gc.comment, gc.createdAt, gc.updatedAt)
            FROM GameComment gc
            JOIN User u ON gc.user.id = u.id
            LEFT JOIN GameRating gr ON gr.game.id = gc.game.id AND gr.user.id = u.id
            WHERE gc.game.id = :gameId
            ORDER BY gc.createdAt DESC
            """)
    Page<GameCommentJOINReturnDTO> getAllComentsByGameId(UUID gameId, Pageable pageable);

}
