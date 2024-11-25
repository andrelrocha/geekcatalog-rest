package rocha.andre.api.service;

import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryInfoDTO;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBResponseFullInfoDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;

public interface IGDBService {
    IGDBResponseFullInfoDTO fetchGameDetails(IGDBQueryInfoDTO queryInfo);
    FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data);
}
