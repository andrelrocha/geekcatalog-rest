package rocha.andre.api.domain.studios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudioRepository extends JpaRepository<Studio, UUID> {

}
