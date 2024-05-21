package rocha.andre.api.domain.imageGame_legacy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rocha.andre.api.domain.game.Game;

import java.util.UUID;

@Entity(name = "ImageGameLegacy")
@Table(name = "image_game_legacy")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ImageGameLegacy {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    public ImageGameLegacy(byte[] imageFile, Game game) {
        this.image = imageFile;
        this.game = game;
    }

    public void updateImage(byte[] imageFile) {
        this.image = imageFile;
    }
}
