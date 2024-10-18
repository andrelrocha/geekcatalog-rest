package rocha.andre.api.domain.utils.sheet.xlsx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImportGamesOnSheetToDB {
    @Autowired
    private ConvertXLSXToGamesListDTO convertSpreadsheetToGamesList;

    public List<GamesOnUserListInfoDTO> saveNewGameDataOnDB(MultipartFile file, List<GamesOnUserListInfoDTO> existingGamesOnUserList) {
        var gamesFromSpreadsheet = convertSpreadsheetToGamesList.convertSpreadsheetToGamesList(file);
        var newDataFromSpreadSheet = filterNewGames(gamesFromSpreadsheet, existingGamesOnUserList);
        var updatedGames = updateExistingGames(gamesFromSpreadsheet, existingGamesOnUserList);

        List<GamesOnUserListInfoDTO> combinedList = new ArrayList<>();
        combinedList.addAll(newDataFromSpreadSheet);
        combinedList.addAll(updatedGames);
        return combinedList;
    }


    private List<GamesOnUserListInfoDTO> filterNewGames(List<GamesOnUserListInfoDTO> gamesOnSheet, List<GamesOnUserListInfoDTO> gamesOnList) {
        return gamesOnSheet.stream()
                .filter(gameOnSheet -> gamesOnList.stream()
                        .noneMatch(existingGame ->
                                existingGame.gameId().equals(gameOnSheet.gameId())
                        )
                )
                .collect(Collectors.toList());
    }

    private List<GamesOnUserListInfoDTO> updateExistingGames(List<GamesOnUserListInfoDTO> gamesFromSpreadsheet, List<GamesOnUserListInfoDTO> existingGamesOnUserList) {
        List<GamesOnUserListInfoDTO> gamesToUpdate = new ArrayList<>();

        for (GamesOnUserListInfoDTO newGame : gamesFromSpreadsheet) {
            for (GamesOnUserListInfoDTO existingGame : existingGamesOnUserList) {
                if (existingGame.gameListId().equals(newGame.gameListId())) {
                    boolean hasChanges =
                            !existingGame.consolePlayed().trim().equalsIgnoreCase(newGame.consolePlayed().trim()) ||
                                    existingGame.rating() != newGame.rating() ||
                                    !existingGame.note().trim().equals(newGame.note().trim());

                    if (hasChanges) {
                        gamesToUpdate.add(newGame);
                    }
                    break;
                }
            }
        }

        return gamesToUpdate;
    }

    /*
    private List<GamesOnUserListInfoDTO> updateGamesDataFromSheet(List<GamesOnUserListInfoDTO> updatableGames) {
        return null;
    }

    /*
    private List<GamesOnUserListInfoDTO> addNewGames(List<GamesOnUserListInfoDTO> data) {
        var gameDTO = new GameDTO()
        var newGame = gameService.createGame()
    }

     */
}
