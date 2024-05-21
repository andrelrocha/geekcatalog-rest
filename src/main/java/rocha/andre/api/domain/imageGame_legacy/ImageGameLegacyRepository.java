package rocha.andre.api.domain.imageGame_legacy;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ImageGameLegacyRepository extends JpaRepository<ImageGameLegacy, UUID> {

    boolean existsByGameId(UUID game_id);

    ImageGameLegacy findImageGameByGameId(UUID game_id);

    @Query("""
        SELECT ig.game.id FROM ImageGameLegacy ig
        ORDER BY ig.game.name
    """)
    Page<UUID> findAllGameIdsOrderedByName(Pageable pageable);

    @Query("""
            SELECT ig FROM ImageGameLegacy ig
            """)
    Page<ImageGameLegacy> findAllImageGames(Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM ImageGameLegacy ig WHERE ig.game.id = :gameId")
    void deleteByGameId(UUID gameId);
}
