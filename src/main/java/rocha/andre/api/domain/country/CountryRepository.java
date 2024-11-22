package rocha.andre.api.domain.country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    @Query("SELECT c FROM Country c ORDER BY c.name ASC")
    Page<Country> findAllCountriesOrderedByName(Pageable pageable);

    @Query("SELECT c FROM Country c WHERE LOWER(TRIM(c.name)) IN :names")
    List<Country> findAllByNamesIgnoreCaseAndTrimmed(List<String> names);
}
