package rocha.andre.api.domain.country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    @Query("SELECT c FROM Country c ORDER BY c.name ASC")
    Page<Country> findAllCountriesOrderedByName(Pageable pageable);
}
