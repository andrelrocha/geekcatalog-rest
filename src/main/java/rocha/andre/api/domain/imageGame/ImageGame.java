package rocha.andre.api.domain.imageGame;

import jakarta.persistence.*;
import lombok.*;
import rocha.andre.api.domain.game.Game;

@Entity(name = "ImageGame")
@Table(name = "image_game")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ImageGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
