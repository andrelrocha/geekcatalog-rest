package rocha.andre.api.service;

import rocha.andre.api.domain.utils.API.IGDB.IGDBQueryInfoDTO;

public interface IGDBService {
    void fetchGameDetails(IGDBQueryInfoDTO queryInfo);
}
