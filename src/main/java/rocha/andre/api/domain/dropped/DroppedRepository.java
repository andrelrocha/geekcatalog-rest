package rocha.andre.api.domain.dropped;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DroppedRepository extends JpaRepository<Dropped ,Long> {
    @Query("""
            SELECT CASE WHEN COUNT(d) > 0
            THEN true ELSE false END
            FROM Dropped d WHERE d.game.id = :gameId
            """)
    boolean gameAlreadyDropped(Long gameId);

    @Query("""
            SELECT d FROM Dropped d
            """)
    Page<Dropped> findAllDroppedGames(Pageable pageable);
}
