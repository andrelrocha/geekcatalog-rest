package rocha.andre.api.domain.imageGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageGameRepository  extends JpaRepository<ImageGame, Long> {

    boolean existsByGameId(long game_id);

    ImageGame findImageGameByGameId(long game_id);
}
