package rocha.andre.api.domain.permission;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    @Query("""
            SELECT p.name FROM Permission p
            """)
    List<String> findAllPermissionsNames(Pageable pageable);
    @Query("""
            SELECT p FROM Permission p
            """)
    List<Permission> findAllPermissions();
}
