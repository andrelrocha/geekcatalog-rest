package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameList.useCase.sheet.GamesOnUserListInfoDTO;
import rocha.andre.api.domain.gameList.useCase.sheet.GetFullGamesOnListByUser;
import rocha.andre.api.service.SpreadsheetService;

import java.util.List;

@Service
public class SpreadSheetServiceImpl implements SpreadsheetService {
    @Autowired
    private GetFullGamesOnListByUser getFullGamesOnListByUser;

    public List<GamesOnUserListInfoDTO> exportGamesOnListWithRatingAndNote(String userId) {
        return getFullGamesOnListByUser.getAllGamesByUserId(userId);
    }
}
