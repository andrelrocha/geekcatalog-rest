package rocha.andre.api.domain.utils.sheet.xlsx;

import org.apache.poi.ss.usermodel.Cell;

public interface CellValidator {
    <T> T validate(Cell cell, T defaultValue);
}
