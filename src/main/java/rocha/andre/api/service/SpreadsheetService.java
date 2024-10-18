package rocha.andre.api.service;

import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.utils.sheet.GamesOnUserListInfoDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface SpreadsheetService {
    ByteArrayInputStream exportGamesOnListWithRatingAndNoteToXlsx(String userId);

    List<GamesOnUserListInfoDTO> saveNewGameDataOnDB(MultipartFile file, String userId);
}
