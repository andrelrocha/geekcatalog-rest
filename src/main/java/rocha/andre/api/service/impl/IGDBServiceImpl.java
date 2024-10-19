package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.utils.API.IGDB.GetGenresAndStudioByGameName;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryInfoDTO;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBResponseFullInfoDTO;
import rocha.andre.api.service.IGDBService;

@Service
public class IGDBServiceImpl implements IGDBService {
    @Autowired
    private GetGenresAndStudioByGameName getGenresAndStudioByGameName;
    @Override
    public IGDBResponseFullInfoDTO fetchGameDetails(IGDBQueryInfoDTO queryInfo) {
        return getGenresAndStudioByGameName.fetchGameDetails(queryInfo);
    }
}
