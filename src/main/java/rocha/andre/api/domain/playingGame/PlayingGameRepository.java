package rocha.andre.api.domain.playingGame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayingGameRepository extends JpaRepository<PlayingGame, Long> {
    @Query("""
            SELECT pg FROM PlayingGame pg
            """)
    Page<PlayingGame> findAllPlayingGames(Pageable pageable);

    PlayingGame findByGameId(long gameId);
}
