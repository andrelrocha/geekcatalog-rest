package rocha.andre.api.domain.authenticationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthenticationTypeRepository extends JpaRepository<AuthenticationType, UUID> {
    @Query("SELECT a FROM AuthenticationType a WHERE LOWER(TRIM(a.name)) = LOWER(TRIM(:name))")
    AuthenticationType findByName(String name);
}
