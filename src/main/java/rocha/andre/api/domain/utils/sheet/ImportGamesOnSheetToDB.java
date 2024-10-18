package rocha.andre.api.domain.utils.sheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ImportGamesOnSheetToDB {
    @Autowired
    private CellValidator cellValidator;


    public List<GamesOnUserListInfoDTO> saveNewGameDataOnDB(MultipartFile file, List<GamesOnUserListInfoDTO> existingGamesOnUserList) {
        var gamesFromSpreadsheet = convertSpreadsheetToGamesList(file);
        var newDataFromSpreadSheet = filterNewGames(gamesFromSpreadsheet, existingGamesOnUserList);
        var updatedGames = updateExistingGames(gamesFromSpreadsheet, existingGamesOnUserList);
        return updatedGames;
    }

    private List<GamesOnUserListInfoDTO> convertSpreadsheetToGamesList(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            List<GamesOnUserListInfoDTO> gamesOnSheet = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                //conferindo se a última linha está vazia ou não
                Cell nameCell = row.getCell(0);
                if (nameCell == null || nameCell.getCellType() == CellType.BLANK) continue;

                String name = cellValidator.validate(row.getCell(0), "N/A");
                String genres = cellValidator.validate(row.getCell(1), "N/A");
                String studios = cellValidator.validate(row.getCell(2), "N/A");
                int yearOfRelease = cellValidator.validate(row.getCell(3), 0);
                String consolePlayed = cellValidator.validate(row.getCell(4), "N/A");
                int rating = cellValidator.validate(row.getCell(5), 0);
                UUID id = cellValidator.validate(row.getCell(6), UUID.randomUUID());
                String note = cellValidator.validate(row.getCell(7), "N/A");

                GamesOnUserListInfoDTO gameInfo = new GamesOnUserListInfoDTO(name, yearOfRelease, genres, studios, consolePlayed, rating, id, note);
                gamesOnSheet.add(gameInfo);
            }

            return gamesOnSheet;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the spreadsheet.", e);
        }
    }



    private List<GamesOnUserListInfoDTO> filterNewGames(List<GamesOnUserListInfoDTO> gamesOnSheet, List<GamesOnUserListInfoDTO> gamesOnList) {
        return gamesOnSheet.stream()
                .filter(gameOnSheet -> gamesOnList.stream()
                        .noneMatch(existingGame ->
                                existingGame.id().equals(gameOnSheet.id())
                        )
                )
                .collect(Collectors.toList());
    }

    private List<GamesOnUserListInfoDTO> updateExistingGames(List<GamesOnUserListInfoDTO> gamesFromSpreadsheet, List<GamesOnUserListInfoDTO> existingGamesOnUserList) {
        List<GamesOnUserListInfoDTO> gamesToUpdate = new ArrayList<>();

        for (GamesOnUserListInfoDTO newGame : gamesFromSpreadsheet) {
            for (GamesOnUserListInfoDTO existingGame : existingGamesOnUserList) {
                if (existingGame.id().equals(newGame.id())) {
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
}
