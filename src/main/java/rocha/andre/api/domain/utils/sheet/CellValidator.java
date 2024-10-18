package rocha.andre.api.domain.utils.sheet;

import org.apache.poi.ss.usermodel.Cell;

public interface CellValidator {
    <T> T validate(Cell cell, T defaultValue);
}
