package rocha.andre.api.domain.gameGenre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.gameConsole.GameConsole;

import java.util.UUID;

public interface GameGenreRepository extends JpaRepository<GameGenre, UUID> {
    @Query("""
            SELECT gg FROM GameGenre gg
            JOIN gg.genre g
            WHERE gg.game.id = :gameId
            ORDER BY g.name ASC
            """)
    Page<GameGenre> findAllGameGenresByGameId(UUID gameId, Pageable pageable);

    @Query("""
        SELECT CASE WHEN COUNT(gg) > 0 THEN TRUE ELSE FALSE END
        FROM GameGenre gg
        WHERE gg.game.id = :gameId AND gg.genre.id = :genreId
        """)
    boolean existsByGameIdAndGenreId(UUID gameId, UUID genreId);
}
