package rocha.andre.api.domain.utils.sheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExportGamesOnListToSheets {

    public ByteArrayInputStream exportToXLSX(List<GamesOnUserListInfoDTO> gamesOnUserList, boolean isAdmin) {
        try (var workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Games On User Lists");

            // Estilização do workbook
            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Cria o cabeçalho com o nome das colunas
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);
            String[] columns;

            if (isAdmin) {
                columns = new String[]{"Name", "Genres", "Studios", "Year of Release", "Console Played", "Rating", "Id", "Note"};
            } else {
                columns = new String[]{"Name", "Genres", "Studios", "Year of Release", "Console Played", "Rating", "Note"};
            }

            // Cria as células do cabeçalho
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Definir larguras das colunas
            int baseWidth = 4000;
            for (int i = 0; i < columns.length; i++) {
                double widthMultiplier = 1.0;

                if (i < 3) {
                    widthMultiplier = 1.6; // Name, Genres e Studios
                } else if (i == 3) {
                    widthMultiplier = 1.2; // Year of release
                }

                sheet.setColumnWidth(i, (int) (baseWidth * widthMultiplier));
            }

            int rowIndex = 1;
            for (GamesOnUserListInfoDTO game : gamesOnUserList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(game.name());
                row.createCell(1).setCellValue(game.genres());
                row.createCell(2).setCellValue(game.studios());
                row.createCell(3).setCellValue(game.yearOfRelease());
                row.createCell(4).setCellValue(game.consolePlayed());
                row.createCell(5).setCellValue(game.rating());

                if (isAdmin) {
                    row.createCell(6).setCellValue(game.id().toString());
                    row.createCell(7).setCellValue(game.note());
                } else {
                    row.createCell(6).setCellValue(game.note());
                }

                for (int i = 0; i < (isAdmin ? 8 : 7); i++) {
                    row.getCell(i).setCellStyle(centeredStyle);
                }
            }

            // Gera o arquivo em um ByteArrayOutputStream
            try (var out = new ByteArrayOutputStream()) {
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error while creating XLSX spreadsheet.", e);
        }
    }
}
