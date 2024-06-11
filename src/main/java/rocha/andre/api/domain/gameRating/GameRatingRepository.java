package rocha.andre.api.domain.gameRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface GameRatingRepository extends JpaRepository<GameRating, UUID> {
    @Query("""
            SELECT CASE WHEN COUNT(gr) > 0 
            THEN true ELSE false END 
            FROM GameRating gr 
            WHERE gr.game.id = :gameId AND gr.user.id = :userId
           """)
    boolean existsByGameIdAndUserId(UUID gameId, UUID userId);

    @Query("""
            SELECT gr FROM GameRating gr 
            WHERE gr.game.id = :gameId AND gr.user.id = :userId
            """)
    GameRating findByGameIdAndUserId(UUID gameId, UUID userId);

    @Query("""
                SELECT gr FROM GameRating gr
                WHERE gr.game.id = :gameId
            """)
    List<GameRating> findAllByGameId(UUID gameId);

    @Query("""
                SELECT gr FROM GameRating gr
                WHERE gr.user.id = :userId
            """)
    List<GameRating> findAllByUserId(UUID userId);
}