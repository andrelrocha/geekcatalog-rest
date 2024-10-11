package rocha.andre.api.domain.userAuthenticationType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rocha.andre.api.domain.authenticationType.AuthenticationType;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.userAuthenticationType.DTO.CreateUserAuthenticationTypeDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "user_authentication_type")
@Entity(name = "UserAuthenticationType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserAuthenticationType {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "authentication_type", referencedColumnName = "id")
    private AuthenticationType authenticationType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    @Column(name = "oauth_id", unique = true)
    private String OAuthId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserAuthenticationType(CreateUserAuthenticationTypeDTO dto) {
        this.authenticationType = dto.authenticationType();
        this.user = dto.user();
        this.OAuthId = dto.OAuthId();
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
