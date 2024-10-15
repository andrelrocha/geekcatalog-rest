package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.utils.sheet.ExportGamesOnListToSheets;
import rocha.andre.api.domain.utils.sheet.GetFullGamesOnListByUser;
import rocha.andre.api.service.SpreadsheetService;

import java.io.ByteArrayInputStream;

@Service
public class SpreadSheetServiceImpl implements SpreadsheetService {
    @Autowired
    private GetFullGamesOnListByUser getFullGamesOnListByUser;
    @Autowired
    private ExportGamesOnListToSheets exportGamesOnListToSheets;

    @Override
    public ByteArrayInputStream exportGamesOnListWithRatingAndNoteToXlsx(String userId) {
        var allGamesOnUserLists = getFullGamesOnListByUser.getAllGamesByUserId(userId);
        return exportGamesOnListToSheets.exportToXLSX(allGamesOnUserLists);
    }
}
