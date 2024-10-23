package rocha.andre.api.infra.utils.sheets.xlsx;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CellValidatorImpl implements CellValidator {

    @Override
    public <T> T validate(Cell cell, T defaultValue) {
        if (cell == null) {
            return defaultValue;
        }

        if (defaultValue instanceof String) {
            if (cell.getCellType() == CellType.STRING) {
                return (T) cell.getStringCellValue().trim();
            }
        } else if (defaultValue instanceof Integer) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (T) Integer.valueOf((int) cell.getNumericCellValue());
            }
        } else if (defaultValue instanceof UUID) {
            if (cell.getCellType() == CellType.STRING) {
                return (T) UUID.fromString(cell.getStringCellValue().trim());
            }
        }

        return defaultValue;
    }
}
