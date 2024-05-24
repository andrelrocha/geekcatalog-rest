package rocha.andre.api.domain.gameList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.listsApp.ListApp;

import java.util.List;
import java.util.UUID;

public interface GameListRepository extends JpaRepository<GameList, UUID> {
    @Query("""
            SELECT g.game.id FROM GameList g
            WHERE g.list.id = :listId
            ORDER BY g.createdAt DESC
            """)
    List<UUID> findTop4ByListIdOrderByCreatedAtDesc(UUID listId);
}