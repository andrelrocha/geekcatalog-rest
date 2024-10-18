package rocha.andre.api.domain.utils.sheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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


    public List<GamesOnUserListInfoDTO> saveNewGameDataOnDB(MultipartFile file, List<GamesOnUserListInfoDTO> existingGamesOnUserList) {
        var gamesFromSpreadsheet = convertSpreadsheetToGamesList(file);
        var newDataFromSpreadSheet = filterNewGames(gamesFromSpreadsheet, existingGamesOnUserList);
        return newDataFromSpreadSheet;
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

                String name = getStringCellValue(row.getCell(0));
                String genres = getStringCellValue(row.getCell(1));
                String studios = getStringCellValue(row.getCell(2));
                int yearOfRelease = getNumericCellValue(row.getCell(3), 0);
                String consolePlayed = getStringCellValue(row.getCell(4));
                int rating = getNumericCellValue(row.getCell(5), 0);
                UUID id = getUuidCellValue(row.getCell(6));
                String note = getStringCellValue(row.getCell(7));

                GamesOnUserListInfoDTO gameInfo = new GamesOnUserListInfoDTO(name, yearOfRelease, genres, studios, consolePlayed, rating, id, note);
                gamesOnSheet.add(gameInfo);
            }

            return gamesOnSheet;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the spreadsheet.", e);
        }
    }

    private String getStringCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.STRING) ? cell.getStringCellValue().trim() : "N/A";
    }
    private int getNumericCellValue(Cell cell, int defaultValue) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? (int) cell.getNumericCellValue() : defaultValue;
    }
    private UUID getUuidCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.STRING) ? UUID.fromString(cell.getStringCellValue().trim()) : null;
    }

    private List<GamesOnUserListInfoDTO> filterNewGames(List<GamesOnUserListInfoDTO> gamesOnSheet, List<GamesOnUserListInfoDTO> gamesOnList) {
        return gamesOnSheet.stream()
                .filter(gameOnSheet -> gamesOnList.stream()
                        .noneMatch(existingGame ->
                                existingGame.name().equalsIgnoreCase(gameOnSheet.name()) &&
                                        existingGame.consolePlayed().equalsIgnoreCase(gameOnSheet.consolePlayed())
                        )
                )
                .collect(Collectors.toList());
    }
}
