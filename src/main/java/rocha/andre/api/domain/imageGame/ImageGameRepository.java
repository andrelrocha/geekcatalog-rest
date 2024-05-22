package rocha.andre.api.domain.imageGame;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ImageGameRepository  extends JpaRepository<ImageGame, UUID> {

    boolean existsByGameId(UUID gameId);

    @Query("""
        SELECT ig.game.id FROM ImageGame ig
        ORDER BY ig.game.name
    """)
    Page<UUID> findAllGameIdsOrderedByName(Pageable pageable);

    @Query("""
            SELECT ig FROM ImageGame ig
            ORDER BY ig.game.name
            """)
    Page<ImageGame> findAllImageGames(Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM ImageGame ig WHERE ig.game.id = :gameId")
    void deleteByGameId(UUID gameId);
}
