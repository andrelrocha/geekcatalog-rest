package rocha.andre.api.domain.profilePic;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.user.User;

import java.sql.ConnectionBuilder;
import java.util.UUID;

@Table(name = "profile_pic")
@Entity(name = "ProfilePic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProfilePic {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ProfilePic(byte[] imageFile, User user) {
        this.image = imageFile;
        this.user = user;
    }

    public void updateImage(byte[] imageFile) {
        this.image = imageFile;
    }

}
