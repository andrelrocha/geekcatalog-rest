package rocha.andre.api.domain.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    boolean existsByName(String name);

    @Query("""
            SELECT g FROM Game g
            WHERE g.done = false
            """)
    Page<Game> findAllGames(Pageable pageable);

    @Query("""
            SELECT MAX(g.id) FROM Game g
            """)
    long findLastId();

    @Query("""
            SELECT g FROM Game g
            WHERE g.id = :id
            """)
    Game findByIdToHandle(UUID id);

    @Query("""
            SELECT g FROM Game g
            WHERE g.done = false
            ORDER BY RANDOM() LIMIT 1
            """)
    Game findRandomGame();

    @Query("SELECT g FROM Game g ORDER BY g.name ASC")
    Page<Game> findAllGamesOrderedByName(Pageable pageable);

    @Query("""
            SELECT g FROM Game g
            WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :nameCompare, '%'))
            AND g.done = false
            """)
    Page<Game> findGamesByNameContaining(String nameCompare, Pageable pageable);
}
