package rocha.andre.api.domain.profilePic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import rocha.andre.api.domain.user.User;

import java.util.UUID;

public interface ProfilePicRepository extends JpaRepository<ProfilePic, UUID> {
    @Query("""
            SELECT pc FROM ProfilePic pc
            WHERE pc.user.id = :userId
            """)
    ProfilePic findProfilePicByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
