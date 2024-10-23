package rocha.andre.api.infra.utils.sheets.xlsx;

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
    @Autowired
    private GetFullGamesOnListByUser getFullGamesOnListByUser;
    @Autowired
    private UpdateGamesAlreadyOnDB updateGamesAlreadyOnDB;


    public List<GamesOnUserListInfoDTO> saveNewGameDataOnDB(MultipartFile file, String userId) {
        var existingGamesOnUserList = getFullGamesOnListByUser.getAllGamesByUserId(userId);
        var gamesFromSpreadsheet = convertSpreadsheetToGamesList.convertSpreadsheetToGamesList(file);

        //FALTA AJEITAR A PARTE DE CRIAR JOGO A PARTIR DA TABELA
        var newDataFromSpreadSheet = filterNewGames(gamesFromSpreadsheet, existingGamesOnUserList);

        var updatedGames = updateGamesAlreadyOnDB.updateGamesDataFromSheet(gamesFromSpreadsheet, existingGamesOnUserList, userId);

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

    /*
    //DEVE CRIAR UM JOGO (NAME, YEAROFRELEASE), CRIAR UM REGISTRO EM GAMECONSOLE, GAMEGENRES, GAMESTUDIOS,
    private List<GamesOnUserListInfoDTO> addNewGames(List<GamesOnUserListInfoDTO> data) {
        //ESTUDAR A QUESTÃO DE CHAMAR A API DO IGDB PARA DESCOBRIR OS CONSOLES DO JOGO

    }
     */
}
