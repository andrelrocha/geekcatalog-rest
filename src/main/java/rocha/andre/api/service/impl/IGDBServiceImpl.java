package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.utils.API.IGDB.GetGameInfoOnIGDB;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryInfoDTO;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBResponseFullInfoDTO;
import rocha.andre.api.service.IGDBService;

@Service
public class IGDBServiceImpl implements IGDBService {
    @Autowired
    private GetGameInfoOnIGDB getGameInfoOnIGDB;
    @Override
    public IGDBResponseFullInfoDTO fetchGameDetails(IGDBQueryInfoDTO queryInfo) {
        return getGameInfoOnIGDB.fetchGameDetails(queryInfo);
    }
}
