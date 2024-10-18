package rocha.andre.api.domain.utils.sheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportGamesOnSheetToDB {


    public List<GamesOnUserListInfoDTO> convertSpreadsheetToGamesList(ByteArrayInputStream spreadsheetInput, List<GamesOnUserListInfoDTO> gamesOnList) {
        try (Workbook workbook = new XSSFWorkbook(spreadsheetInput)) {
            Sheet sheet = workbook.getSheetAt(0);

            List<GamesOnUserListInfoDTO> gamesList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String name = row.getCell(0).getStringCellValue().trim();
                String genres = row.getCell(1).getStringCellValue().trim();
                String studios = row.getCell(2).getStringCellValue().trim();
                int yearOfRelease = (int) row.getCell(3).getNumericCellValue();
                String consolePlayed = row.getCell(4).getStringCellValue().trim();
                int rating = (int) row.getCell(5).getNumericCellValue();
                String note = row.getCell(6).getStringCellValue().trim();

                gamesList.add(new GamesOnUserListInfoDTO(name, yearOfRelease, genres, studios, consolePlayed, rating, null, note));
            }

            return gamesList;

        } catch (IOException e) {
            throw new RuntimeException("Error while reading the spreadsheet.", e);
        }
    }
}
