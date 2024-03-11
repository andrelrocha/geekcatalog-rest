package rocha.andre.api.domain.finished;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FinishedRepository extends JpaRepository<Finished, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(f) > 0
            THEN true ELSE false END
            FROM Finished f WHERE f.game.id = :gameId
            """)
    boolean gameAlreadyExists(Long gameId);

    boolean existsByGameId(long game_id);

    @Query("""
            SELECT f FROM Finished f
            """)
    Page<Finished> findAllFinished(Pageable pageable);
}
