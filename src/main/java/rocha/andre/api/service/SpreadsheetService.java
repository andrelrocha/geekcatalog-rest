package rocha.andre.api.service;

import rocha.andre.api.domain.gameList.useCase.sheet.GamesOnUserListInfoDTO;

import java.util.List;

public interface SpreadsheetService {
    List<GamesOnUserListInfoDTO> exportGamesOnListWithRatingAndNote(String userId);
}
