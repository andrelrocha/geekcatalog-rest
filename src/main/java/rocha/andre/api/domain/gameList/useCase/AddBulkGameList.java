package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameList.DTO.GameListBulkCreateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListBulkReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListCreateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AddBulkGameList {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ListAppRepository listAppRepository;

    public ArrayList<GameListBulkReturnDTO> addBulkGamesToList(GameListBulkCreateDTO data) {
        var userIdUUID = UUID.fromString(data.userId());
        var user = userRepository.findById(userIdUUID)
                .orElseThrow(()-> new ValidationException("Não foi encontrado usuário com o id informado na adição de bulk game list"));

        var listIdUUID = UUID.fromString(data.listId());
        var list = listAppRepository.findById(listIdUUID)
                .orElseThrow(()-> new ValidationException("Não foi encontrada lista com o id informado na adição de bulk game list"));

        if (!user.getId().equals(list.getUser().getId())) {
            //VALIDAÇÃO SE O USUÁRIO TEM PERMISSÃO NA LISTS_PERMISSION
            throw new ValidationException("O usuário que está adicionando jogos não é o dono da lista ou não tem permissão");
        }

        var gameListCreate = new ArrayList<GameList>();
        for (String gameId : data.gamesId()) {
            var gameIdUUID = UUID.fromString(gameId);
            var game = gameRepository.findById(gameIdUUID)
                    .orElseThrow(()-> new ValidationException("Não foi encontrado jogo com o id informado na adição de bulk game list"));

            var gameListCreateDTO = new GameListCreateDTO(user, game, list, null, null);
            var gameList = new GameList(gameListCreateDTO);
            gameListCreate.add(gameList);
        }

        var gamesOnDB = gameListRepository.saveAll(gameListCreate);

        var gameListReturnDTOs = new ArrayList<GameListBulkReturnDTO>();
        for (GameList gameList : gamesOnDB) {
            gameListReturnDTOs.add(new GameListBulkReturnDTO(gameList));
        }

        return gameListReturnDTOs;
    }
}
