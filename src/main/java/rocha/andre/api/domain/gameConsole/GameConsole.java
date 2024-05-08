package rocha.andre.api.domain.gameConsole;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rocha.andre.api.domain.consoles.Console;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.gameConsole.DTO.CreateGameConsoleDTO;

import java.util.UUID;

@Table(name = "game_console")
@Entity(name = "GameConsole")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class GameConsole {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "console_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Console console;

    public GameConsole(CreateGameConsoleDTO data) {
        this.game = data.game();
        this.console = data.console();
    }
}
