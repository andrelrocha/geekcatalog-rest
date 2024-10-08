package rocha.andre.api.domain.game;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import rocha.andre.api.domain.game.DTO.GameDTO;

import java.time.LocalDateTime;
import java.util.UUID;


@Table(name = "games")
@Entity(name = "Game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Game {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "yr_of_release")
    private Integer yearOfRelease;

    @Column(name = "metacritic")
    private Integer metacritic;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Game(GameDTO gameDTO) {
        this.name = gameDTO.name();
        this.yearOfRelease = gameDTO.yearOfRelease();
        this.metacritic = gameDTO.metacritic();
    }

    public void updateGame(GameDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.yearOfRelease() != 0) {
            this.yearOfRelease = dto.yearOfRelease();
        }
        if (dto.metacritic() != 0) {
            this.metacritic = dto.metacritic();
        }
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
