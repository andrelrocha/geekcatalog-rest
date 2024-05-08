package rocha.andre.api.domain.gameStudio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GameStudioRepository extends JpaRepository<GameStudio, UUID> {
    @Query("SELECT gs FROM GameStudio gs ORDER BY gs.studio.name ASC")
    Page<GameStudio> findAllGameStudiosOrderedByName(Pageable pageable);
}
