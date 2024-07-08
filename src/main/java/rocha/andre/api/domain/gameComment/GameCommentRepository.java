package rocha.andre.api.domain.gameComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GameCommentRepository extends JpaRepository<GameComment, UUID> {
    @Query("""
            SELECT CASE WHEN COUNT(gc) > 0 THEN TRUE ELSE FALSE END
            FROM GameComment gc
            WHERE gc.comment = :comment AND gc.user.id = :userId AND gc.game.id = :gameId
            """)
    boolean gameCommentExists(UUID userId, UUID gameId, String comment);

}
