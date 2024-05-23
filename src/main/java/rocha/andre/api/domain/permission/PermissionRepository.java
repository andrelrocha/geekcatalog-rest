package rocha.andre.api.domain.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.country.Country;

import java.util.List;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    @Query("""
            SELECT p.permission FROM Permission p
            """)
    List<String> findAllPermissionsNames(Pageable pageable);
    @Query("""
            SELECT p FROM Permission p
            """)
    List<Permission> findAllPermissions();

    @Query("SELECT p FROM Permission p ORDER BY p.permission ASC")
    Page<Permission> findAllPermissionOrderedByName(Pageable pageable);
}
