package rocha.andre.api.domain.gameList.customRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class GameListNativeSqlRepositoryImpl implements GameListNativeSqlRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Object[]> findAllGamesInfoByUserId(UUID userId) {
        String sql = """
            SELECT 
                g.name, 
                g.metacritic, 
                g.yr_of_release, 
                COALESCE(STRING_AGG(DISTINCT gen.name, ', '), '') AS genres, 
                COALESCE(STRING_AGG(DISTINCT stu.name, ', '), '') AS studios, 
                COALESCE(c.name, '') AS console_name,  
                COALESCE(gr.rating, 0) AS rating, 
                COALESCE(gl.note, '') AS note,
                g.id
            FROM 
                game_list AS gl
            JOIN 
                games AS g ON g.id = gl.game_id
            LEFT JOIN 
                game_genre AS gg ON gg.game_id = g.id
            LEFT JOIN 
                genres AS gen ON gen.id = gg.genre_id 
            LEFT JOIN 
                game_studio AS gs ON gs.game_id = g.id
            LEFT JOIN 
                studios AS stu ON stu.id = gs.studio_id 
            LEFT JOIN 
                consoles AS c ON c.id = gl.console_played_id  
            LEFT JOIN 
                users_game_rating AS gr ON gr.game_id = g.id
            WHERE 
                gl.user_id = :userId
            GROUP BY 
                g.id, g.name, g.metacritic, g.yr_of_release, c.name, gr.rating, gl.note
            ORDER BY 
                g.name
        """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);

        List<Object[]> resultados = query.getResultList();

        return resultados;
    }
}
