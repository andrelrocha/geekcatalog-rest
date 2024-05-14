package rocha.andre.api.domain.gameConsole;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface GameConsoleRepository extends JpaRepository<GameConsole, UUID> {
    @Query("""
            SELECT gc FROM GameConsole gc
            JOIN gc.console c
            WHERE gc.game.id = :gameId
            ORDER BY c.name ASC
            """)
    Page<GameConsole> findAllGameConsolesByGameId(UUID gameId, Pageable pageable);

    @Query("""
        SELECT CASE WHEN COUNT(gc) > 0 THEN TRUE ELSE FALSE END 
        FROM GameConsole gc 
        WHERE gc.game.id = :gameId AND gc.console.id = :consoleId
        """)
    boolean existsByGameIdAndConsoleId(UUID gameId, UUID consoleId);

    @Query("""
        SELECT gc.console.name
        FROM GameConsole gc
        WHERE gc.game.id = :gameId
        """)
    ArrayList<String> findAllConsolesNamesByGameId(UUID gameId);

    @Query("""
        SELECT NEW rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO(gc.console.id, gc.console.name)
        FROM GameConsole gc
        WHERE gc.game.id = :gameId
        """)
    ArrayList<ConsoleReturnDTO> findAllConsolesInfoByGameId(UUID gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GameConsole gc WHERE gc.game.id = :gameId AND gc.console.id NOT IN :consoleIds")
    void deleteConsolesByGameIdAndConsoleIds(UUID gameId, List<UUID> consoleIds);

    @Query("""
            SELECT gc.console.id FROM GameConsole gc
            WHERE gc.game.id = :gameId
            """)
    ArrayList<UUID> findAllConsoleIdsByGameId(UUID gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GameConsole gc WHERE gc.game.id = :gameId")
    void deleteAllByGameId(UUID gameId);
}
