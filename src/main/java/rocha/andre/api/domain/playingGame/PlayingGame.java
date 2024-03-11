package rocha.andre.api.domain.playingGame;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameAddDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameUpdateDTO;

import java.time.LocalDateTime;

@Table(name = "playing_games")
@Entity(name = "PlayingGame")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PlayingGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name="first_played")
    private LocalDateTime firstPlayed;

    public PlayingGame(PlayingGameAddDTO data) {
        this.game = data.game();
        this.firstPlayed = data.firstPlayed();
    }

    public void updatePlayingGame(PlayingGameUpdateDTO data) {
        this.firstPlayed = data.firstPlayed();
    }
}
