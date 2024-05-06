package rocha.andre.api.domain.studios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface StudioRepository extends JpaRepository<Studio, UUID> {
    @Query("""
            SELECT s FROM Studio s ORDER BY s.name ASC
            """)
    Page<Studio> findAllStudiosOrderedByName(Pageable pageable);
}
