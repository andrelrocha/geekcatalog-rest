package rocha.andre.api.domain.authenticationType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthenticationTypeRepository extends JpaRepository<AuthenticationType, UUID> {
}
