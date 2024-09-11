package rocha.andre.api.domain.gameComment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameComment.DTO.GameCommentJOINReturnDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GameCommentRepositoryTest {

    @Autowired
    private GameCommentRepository gameCommentRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    private Game game;
    private User user;

    @BeforeEach
    void setUp() {
        // Setup: criar e salvar um jogo e um usuário no banco de dados
        game = new Game();
        game.setId(UUID.randomUUID());
        game.setName("Test Game");
        gameRepository.save(game);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test User");
        userRepository.save(user);

        // Setup: criar e salvar alguns comentários no banco de dados
        GameComment comment1 = new GameComment();
        comment1.setId(UUID.randomUUID());
        comment1.setUser(user);
        comment1.setGame(game);
        comment1.setComment("First comment");
        comment1.setCreatedAt(LocalDateTime.now());
        gameCommentRepository.save(comment1);

        GameComment comment2 = new GameComment();
        comment2.setId(UUID.randomUUID());
        comment2.setUser(user);
        comment2.setGame(game);
        comment2.setComment("Second comment");
        comment2.setCreatedAt(LocalDateTime.now());
        gameCommentRepository.save(comment2);
    }

    @Test
    void shouldGetAllCommentsByGameId() {
        // Definir as condições de paginação
        PageRequest pageRequest = PageRequest.of(0, 10);

        // Chamar o método a ser testado
        Page<GameCommentJOINReturnDTO> result = gameCommentRepository.getAllComentsByGameId(game.getId(), pageRequest);

        // Verificar o tamanho do resultado
        assertThat(result.getContent()).hasSize(2);

        // Verificar que os comentários foram retornados corretamente
        GameCommentJOINReturnDTO comment1 = result.getContent().get(0);
        GameCommentJOINReturnDTO comment2 = result.getContent().get(1);

        assertThat(comment1.comment()).isEqualTo("First comment");
        assertThat(comment2.comment()).isEqualTo("Second comment");
    }
}
