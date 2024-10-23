package rocha.andre.api.infra.utils.sheets.xlsx;

import org.apache.poi.ss.usermodel.Cell;

public interface CellValidator {
    <T> T validate(Cell cell, T defaultValue);
}
