package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.utils.API.IGDB.GetGenresAndStudioByGameName;
import rocha.andre.api.domain.utils.API.IGDB.IGDBQueryInfoDTO;
import rocha.andre.api.service.IGDBService;

@Service
public class IGDBServiceImpl implements IGDBService {
    @Autowired
    private GetGenresAndStudioByGameName getGenresAndStudioByGameName;
    @Override
    public void fetchGameDetails(IGDBQueryInfoDTO queryInfo) {
        getGenresAndStudioByGameName.fetchGameDetails(queryInfo);
    }
}
