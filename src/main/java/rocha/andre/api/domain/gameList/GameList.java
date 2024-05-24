package rocha.andre.api.domain.gameList;

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
import rocha.andre.api.domain.listsApp.DTO.ListAppCreateDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.User;

import java.time.LocalDateTime;
import java.util.UUID;


@Table(name = "game_list")
@Entity(name = "GameList")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class GameList {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ListApp list;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "console_played_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Console console;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



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
