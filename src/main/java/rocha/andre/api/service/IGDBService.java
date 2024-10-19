package rocha.andre.api.service;

import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryInfoDTO;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBResponseFullInfoDTO;

public interface IGDBService {
    IGDBResponseFullInfoDTO fetchGameDetails(IGDBQueryInfoDTO queryInfo);
}
