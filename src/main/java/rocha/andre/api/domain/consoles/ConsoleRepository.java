package rocha.andre.api.domain.consoles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConsoleRepository extends JpaRepository<Console, UUID> {
    @Query("SELECT c FROM Console c ORDER BY c.name ASC")
    Page<Console> findAllConsolesOrderedByName(Pageable pageable);

    @Query("SELECT c FROM Console c WHERE LOWER(c.name) = LOWER(:name)")
    Console findByName(String name);

    @Query("SELECT c FROM Console c WHERE LOWER(TRIM(c.name)) IN :names")
    List<Console> findAllByNamesIgnoreCaseAndTrimmed(List<String> names);
}
