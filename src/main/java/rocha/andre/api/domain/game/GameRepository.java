package rocha.andre.api.domain.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("""
        SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END 
        FROM Game g 
        WHERE LOWER(TRIM(g.name)) = LOWER(TRIM(:name))
        """)
    boolean existsByName(String name);

    @Query("""
            SELECT g FROM Game g
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
            ORDER BY RANDOM() LIMIT 1
            """)
    Game findRandomGame();

    @Query("SELECT g FROM Game g ORDER BY g.name ASC")
    Page<Game> findAllGamesOrderedByName(Pageable pageable);

    @Query("""
            SELECT g FROM Game g
            WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :nameCompare, '%'))
            """)
    Page<Game> findGamesByNameContaining(String nameCompare, Pageable pageable);
}
