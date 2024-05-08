package rocha.andre.api.domain.gameConsoles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GameConsoleRepository extends JpaRepository<GameConsole, UUID> {
    @Query("""
            SELECT gc FROM GameConsole gc
            JOIN gc.console c
            WHERE gc.game.id = :gameId
            ORDER BY c.name ASC
            """)
    Page<GameConsole> findAllGameConsolesByGameId(UUID gameId, Pageable pageable);
}
