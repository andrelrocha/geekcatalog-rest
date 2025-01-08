package rocha.andre.api.service;

import rocha.andre.api.domain.utils.API.OpenAI.DTO.GameNameDTO;

public interface ChatGPTService {
    String getGameInfo(GameNameDTO gameNameDTO);
}
