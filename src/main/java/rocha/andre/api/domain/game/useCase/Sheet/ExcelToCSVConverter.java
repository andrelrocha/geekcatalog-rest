package rocha.andre.api.domain.game.useCase.Sheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelToCSVConverter {

    public String convertXlsxToCsv() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("xlsx/gamesbacklog.xlsx");
             var workbook = new XSSFWorkbook(inputStream);
             var csvWriter = new FileWriter("src/main/resources/csv/backlog.csv")) {
            // Abre o arquivo de Excel (xlsx) como InputStream
            // Cria um objeto Workbook para manipular o arquivo Excel
            // Prepara um FileWriter para escrever no arquivo CSV

            // Obtém a primeira planilha (Sheet) do arquivo Excel
            var sheet = workbook.getSheetAt(0);

            // Cria uma lista para armazenar os cabeçalhos das colunas
            List<String> header = new ArrayList<>();
            Row headerRow = sheet.getRow(0);

            // Itera pelas células da linha de cabeçalho e adiciona os valores à lista de cabeçalhos
            for (Cell cell : headerRow) {
                header.add(cell.getStringCellValue());
            }

            // Define as colunas relevantes que deseja extrair
            List<String> relevantColumns = List.of("name", "length", "metacritic", "excitement", "played", "genre");

            // Cria uma lista para armazenar os índices das colunas relevantes
            List<Integer> relevantColumnIndexes = new ArrayList<>();
            for (String column : relevantColumns) {
                // Obtém o índice da coluna pelo nome
                int index = getColumnIndexByName(column, header);

                // Se encontrar o índice da coluna, adiciona à lista de índices relevantes
                if (index != -1) {
                    relevantColumnIndexes.add(index);
                }
            }

            // Escreve os cabeçalhos das colunas relevantes no arquivo CSV
            csvWriter.append(String.join(",", relevantColumns)).append("\n");

            // Itera pelas linhas da planilha, começando da segunda linha (ignorando o cabeçalho)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // Verifica se a linha não está vazia
                if (row != null && !isEmpty(row)) {

                    // Cria uma lista para armazenar os dados da linha
                    List<String> rowData = new ArrayList<>();

                    for (int columnIndex : relevantColumnIndexes) {
                        // Obtém a célula correspondente à coluna relevante
                        Cell cell = row.getCell(columnIndex);

                        if (cell != null) {
                            // Verifica se a coluna é a de "played" e ajusta os valores booleanos
                            if (columnIndex == getColumnIndexByName("played", header)) {
                                String playedValue = cell.getStringCellValue().trim();
                                if (playedValue.equalsIgnoreCase("YES")) {
                                    rowData.add("true");
                                } else if (playedValue.equalsIgnoreCase("NO")) {
                                    rowData.add("false");
                                }
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                // Verifica se o tipo da célula é numérico
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    // Verifica se é uma data
                                    // Adiciona a formatação de data à lista de dados da linha
                                    rowData.add(cell.getDateCellValue().toString());
                                } else {
                                    // Adiciona o valor numérico formatado como inteiro à lista de dados da linha
                                    rowData.add(String.valueOf((int) cell.getNumericCellValue()));
                                }
                            } else {
                                // Adiciona o valor da célula à lista de dados da linha
                                rowData.add(getCellValueAsString(cell));
                            }
                        } else {
                            // Se a célula for nula, adiciona um valor vazio à lista de dados da linha
                            rowData.add("");
                        }
                    }
                    // Escreve a linha de dados no arquivo CSV
                    csvWriter.append(String.join(",", rowData)).append("\n");
                } else {
                    break;
                }
            }

            return "DEU TUDO CERTO NO PROCESSO DE CONVERSÃO DE XLSX PARA CSV!!!";
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                File directory = new File("csv");
                if (!directory.exists()) {
                    directory.mkdirs();
                    convertXlsxToCsv();
                } else {
                    e.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }

            return "Algo deu errado no processo de conversão de xlsx para csv.";
        }
    }

    private int getColumnIndexByName(String columnName, List<String> header) {
        return header.indexOf(columnName);
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private boolean isEmpty(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
