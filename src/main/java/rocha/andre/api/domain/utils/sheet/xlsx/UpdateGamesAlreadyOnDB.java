package rocha.andre.api.domain.utils.sheet.xlsx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.Console;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateRequestDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingDTO;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.service.GameListService;
import rocha.andre.api.service.GameRatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

//SÓ PODE EDITAR CONSOLE PLAYED, NOTE E RATING
@Component
public class UpdateGamesAlreadyOnDB {
    @Autowired
    private ConsoleRepository consoleRepository;
    @Autowired
    private GameListService gameListService;
    @Autowired
    private GameRatingService gameRatingService;

    public List<GamesOnUserListInfoDTO> updateGamesDataFromSheet(List<GamesOnUserListInfoDTO> gamesFromSpreadsheet, List<GamesOnUserListInfoDTO> existingGamesOnUserList, String userId) {
        List<GamesOnUserListInfoDTO> updatedGames = new ArrayList<>();

        List<Console> consoles = consoleRepository.findAll();
        Map<String, UUID> consoleNameToIdMap = consoles.stream()
                .collect(Collectors.toMap(c -> c.getName().trim().toLowerCase(), Console::getId));

        for (GamesOnUserListInfoDTO newGame : gamesFromSpreadsheet) {
            for (GamesOnUserListInfoDTO existingGame : existingGamesOnUserList) {
                if (existingGame.gameListId().equals(newGame.gameListId())) {

                    UUID newConsoleId = consoleNameToIdMap.get(newGame.consolePlayed().trim().toLowerCase());
                    UUID existingConsoleId = consoleNameToIdMap.get(existingGame.consolePlayed().trim().toLowerCase());

                    if (newConsoleId == null) {
                        throw new ValidationException("Console '" + newGame.consolePlayed() + "' not found in the system, for game " + newGame.name());
                    }

                    boolean gameListHasChanges =
                            !newConsoleId.equals(existingConsoleId) ||
                                    !existingGame.note().trim().equals(newGame.note().trim());

                    boolean ratingHasChanged = existingGame.rating() != newGame.rating();

                    if (gameListHasChanges) {
                        GameListUpdateRequestDTO updateRequestDTO = new GameListUpdateRequestDTO(
                                newConsoleId.toString(),
                                newGame.note(),
                                userId
                        );

                        try {
                            gameListService.updateGameList(updateRequestDTO, newGame.gameListId().toString());
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to update game list for game: " + newGame.name(), e);
                        }
                    }

                    if (ratingHasChanged) {
                        GameRatingDTO ratingDTO = new GameRatingDTO(
                                newGame.gameId().toString(),
                                userId,
                                newGame.rating()
                        );

                        try {
                            gameRatingService.addGameRating(ratingDTO);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to update game rating for game: " + newGame.name(), e);
                        }
                    }

                    if (gameListHasChanges || ratingHasChanged) {
                        updatedGames.add(newGame);
                    }

                    break;
                }
            }
        }

        return updatedGames;
    }
}
