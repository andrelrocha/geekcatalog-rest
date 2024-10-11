package rocha.andre.api.domain.userAuthenticationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rocha.andre.api.domain.user.User;

import java.util.UUID;

public interface UserAuthenticationTypeRepository extends JpaRepository<UserAuthenticationType, UUID> {
    @Query("""
        SELECT uat.user FROM UserAuthenticationType uat
        JOIN uat.user u
        WHERE uat.OAuthId = :oauthId
    """)
    User findUserByOAuthId(String oauthId);
}
