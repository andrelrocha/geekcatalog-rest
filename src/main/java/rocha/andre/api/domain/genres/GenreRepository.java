package rocha.andre.api.domain.genres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    @Query("SELECT g FROM Genre g ORDER BY g.name ASC")
    Page<Genre> findGenresOrderedByName(Pageable pageable);
}
