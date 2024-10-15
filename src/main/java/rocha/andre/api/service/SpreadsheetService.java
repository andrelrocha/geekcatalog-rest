package rocha.andre.api.service;

import rocha.andre.api.domain.utils.sheet.GamesOnUserListInfoDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface SpreadsheetService {
    ByteArrayInputStream exportGamesOnListWithRatingAndNoteToXlsx(String userId);
}
