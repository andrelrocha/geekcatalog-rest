package rocha.andre.api.domain.gameStudio;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.gameConsole.GameConsole;
import rocha.andre.api.domain.gameGenre.GameGenre;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface GameStudioRepository extends JpaRepository<GameStudio, UUID> {
    @Query("""
            SELECT gs FROM GameStudio gs
            JOIN gs.studio s
            WHERE gs.game.id = :gameId
            ORDER BY s.name ASC
            """)
    Page<GameStudio> findAllGameStudiosByGameId(UUID gameId, Pageable pageable);

    @Query("""
        SELECT CASE WHEN COUNT(gs) > 0 THEN TRUE ELSE FALSE END
        FROM GameStudio gs
        WHERE gs.game.id = :gameId AND gs.studio.id = :studioId
        """)
    boolean existsByGameIdAndStudioId(UUID gameId, UUID studioId);

    @Query("""
    SELECT NEW rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo(gs.studio.id, gs.studio.name)
    FROM GameStudio gs 
    WHERE gs.game.id = :gameId
    """)
    ArrayList<StudioReturnFullGameInfo> findAllStudioByGameId(UUID gameId);

    @Query("""
            SELECT gs.studio.name
            FROM GameStudio gs 
            WHERE gs.game.id = :gameId
            """)
    ArrayList<String> findAllStudioNamesByGameId(UUID gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GameStudio gs WHERE gs.game.id = :gameId AND gs.studio.id NOT IN :studioIds")
    void deleteStudiosByGameIdAndStudioIds(UUID gameId, List<UUID> studioIds);

    @Query("""
            SELECT gs.studio.id FROM GameStudio gs
            WHERE gs.game.id = :gameId
            """)
    ArrayList<UUID> findAllStudioIdsByGameId(UUID gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GameStudio gs WHERE gs.game.id = :gameId")
    void deleteAllByGameId(UUID gameId);

    @Query("""
            SELECT gs FROM GameStudio gs
            WHERE gs.game.id = :gameId
            """)
    List<GameStudio> findAllByGameId(UUID gameId);
}
