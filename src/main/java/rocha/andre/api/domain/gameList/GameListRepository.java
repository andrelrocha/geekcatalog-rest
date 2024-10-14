package rocha.andre.api.domain.gameList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.gameGenre.GameGenre;
import rocha.andre.api.domain.gameList.customRepository.GameListNativeSqlRepository;
import rocha.andre.api.domain.gameList.useCase.sheet.GamesOnUserListInfoDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listsApp.ListApp;

import java.util.List;
import java.util.UUID;

public interface GameListRepository extends JpaRepository<GameList, UUID>, GameListNativeSqlRepository {

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

    boolean existsByGameIdAndListId(UUID gameId, UUID listId);

    @Query("""
            SELECT g FROM GameList g
            WHERE g.list.id = :listId
            """)
    List<GameList> findAllByListId(UUID listId);

    @Query("""
            SELECT g FROM GameList g
            WHERE g.game.id = :gameId
            """)
    List<GameList> findAllByGameId(UUID gameId);

    @Query("""
            SELECT g FROM GameList g
            WHERE g.user.id = :userId
            """)
    List<GameList> findAllByUserId(UUID userId);
}