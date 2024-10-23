package rocha.andre.api.infra.utils.sheets.xlsx;

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

@Component
public class ConvertXLSXToGamesListDTO {
    @Autowired
    private CellValidator cellValidator;

    public List<GamesOnUserListInfoDTO> convertSpreadsheetToGamesList(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            List<GamesOnUserListInfoDTO> gamesOnSheet = new ArrayList<>();

            validateColumns(sheet);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                // Conferindo se a última linha está vazia ou não
                Cell nameCell = row.getCell(0);
                if (nameCell == null || nameCell.getCellType() == CellType.BLANK) continue;

                String name = cellValidator.validate(row.getCell(0), "N/A");
                String genres = cellValidator.validate(row.getCell(1), "N/A");
                String studios = cellValidator.validate(row.getCell(2), "N/A");
                int yearOfRelease = cellValidator.validate(row.getCell(3), 0);
                String consolePlayed = cellValidator.validate(row.getCell(4), "N/A");
                int rating = cellValidator.validate(row.getCell(5), 0);
                UUID gameListId = cellValidator.validate(row.getCell(6), UUID.randomUUID());
                UUID gameId = cellValidator.validate(row.getCell(7), UUID.randomUUID());
                String note = cellValidator.validate(row.getCell(8), "N/A");

                GamesOnUserListInfoDTO gameInfo = new GamesOnUserListInfoDTO(name, yearOfRelease, genres, studios, consolePlayed, rating, gameListId, gameId, note);
                gamesOnSheet.add(gameInfo);
            }

            return gamesOnSheet;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the spreadsheet.", e);
        }
    }

    private void validateColumns(Sheet sheet) {
        String[] columns = new String[]{"Name", "Genres", "Studios", "Year of Release", "Console Played", "Rating", "GameList Id", "Game Id", "Note"};
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new RuntimeException("Header row is missing.");
        }

        for (String column : columns) {
            boolean found = false;
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().equalsIgnoreCase(column)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new RuntimeException("Missing required column: " + column);
            }
        }
    }
}
