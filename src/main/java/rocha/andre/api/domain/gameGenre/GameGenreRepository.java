package rocha.andre.api.domain.gameGenre;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface GameGenreRepository extends JpaRepository<GameGenre, UUID> {
    @Query("""
            SELECT gg FROM GameGenre gg
            JOIN gg.genre g
            WHERE gg.game.id = :gameId
            ORDER BY g.name ASC
            """)
    Page<GameGenre> findAllGameGenresByGameId(UUID gameId, Pageable pageable);

    @Query("""
        SELECT CASE WHEN COUNT(gg) > 0 THEN TRUE ELSE FALSE END
        FROM GameGenre gg
        WHERE gg.game.id = :gameId AND gg.genre.id = :genreId
        """)
    boolean existsByGameIdAndGenreId(UUID gameId, UUID genreId);

    @Query("""
        SELECT NEW rocha.andre.api.domain.genres.DTO.GenreReturnDTO(gg.genre.id, gg.genre.name)
        FROM GameGenre gg
        WHERE gg.game.id = :gameId
        """)
    ArrayList<GenreReturnDTO> findAllGenresInfoByGameId(UUID gameId);

    @Query("""
            SELECT gg.genre.name
            FROM GameGenre gg 
            WHERE gg.game.id = :gameId
            """)
    ArrayList<String> findAllGenresNamesByGameId(UUID gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GameGenre gg WHERE gg.game.id = :gameId AND gg.genre.id NOT IN :genreIds")
    void deleteGenresByGameIdAndGenreIds(UUID gameId, List<UUID> genreIds);

    @Query("""
            SELECT gg.genre.id FROM GameGenre gg
            WHERE gg.game.id = :gameId
            """)
    ArrayList<UUID> findAllGenreIdsByGameId(UUID gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GameGenre gg WHERE gg.game.id = :gameId")
    void deleteAllByGameId(UUID gameId);

    @Query("""
            SELECT gg FROM GameGenre gg
            WHERE gg.game.id = :gameId
            """)
    List<GameGenre> findAllByGameId(UUID gameId);
}
