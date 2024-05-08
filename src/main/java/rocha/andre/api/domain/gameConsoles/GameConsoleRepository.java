package rocha.andre.api.domain.gameConsoles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.gameConsoles.GameConsole;

import java.util.UUID;

public interface GameConsoleRepository extends JpaRepository<GameConsole, UUID> {
    @Query("SELECT gc FROM GameConsole gc ORDER BY gc.console.name ASC")
    Page<GameConsole> findAllGameConsolesOrderedByName(Pageable pageable);
}
