package rocha.andre.api.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByLogin(String login);
    UserDetails findByUsername(String username);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true
            ELSE false END
            FROM User u WHERE u.login = :login
            """)
    boolean userExistsByLogin(String login);

    @Query("""
            SELECT u FROM User u WHERE u.login = :login
            """)
    User findByLoginToHandle(String login);

    @Query("""
            SELECT u FROM User u WHERE u.id = :id
            """)
    User findByIdToHandle(UUID id);

    boolean existsByLogin(String login);

}
