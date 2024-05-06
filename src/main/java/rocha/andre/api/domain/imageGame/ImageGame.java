package rocha.andre.api.domain.imageGame;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import rocha.andre.api.domain.game.Game;

import java.util.UUID;

@Entity(name = "ImageGame")
@Table(name = "image_game")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ImageGame {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    public ImageGame(byte[] imageFile, Game game) {
        this.image = imageFile;
        this.game = game;
    }

    public void updateImage(byte[] imageFile) {
        this.image = imageFile;
    }
}
