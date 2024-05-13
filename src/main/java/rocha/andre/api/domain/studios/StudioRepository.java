package rocha.andre.api.domain.studios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;

import java.util.ArrayList;
import java.util.UUID;

public interface StudioRepository extends JpaRepository<Studio, UUID> {
    @Query("""
            SELECT s FROM Studio s ORDER BY s.name ASC
            """)
    Page<Studio> findAllStudiosOrderedByName(Pageable pageable);

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END
        FROM Studio s
        WHERE s.name = :name AND s.country.id = :countryId
        """)
    boolean existsByNameAndCountry(String name, UUID countryId);

    @Query("""
        SELECT NEW rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo(gs.studio.id, gs.studio.name)
        FROM GameStudio gs
        WHERE gs.game.id = :gameId
        """)
    ArrayList<StudioReturnFullGameInfo> findAllStudiosInfoByGameId(UUID gameId);
}
