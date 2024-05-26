package rocha.andre.api.domain.gameList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.gameGenre.GameGenre;
import rocha.andre.api.domain.listsApp.ListApp;

import java.util.List;
import java.util.UUID;

public interface GameListRepository extends JpaRepository<GameList, UUID> {
    @Query("""
            SELECT g FROM GameList g
            WHERE g.list.id = :listId
            ORDER BY g.game.name ASC
            """)
    Page<GameList> findAllGameListByListId(UUID listId, Pageable pageable);

    @Query("""
            SELECT g FROM GameList g
            WHERE g.list.id = :listId
            """)
    Page<GameList> pageableGameListByListId(UUID listId, Pageable pageable);

    @Query("""
            SELECT g FROM GameList g
            WHERE g.list.id = :listId
            ORDER BY g.createdAt DESC
            """)
    Page<GameList> findAllGameListByListIdOrderByLatest(UUID listId, Pageable pageable);

    @Query("""
            SELECT COUNT(g) FROM GameList g
            WHERE g.list.id = :listId
            """)
    int countGameListsByListId(UUID listId);
}