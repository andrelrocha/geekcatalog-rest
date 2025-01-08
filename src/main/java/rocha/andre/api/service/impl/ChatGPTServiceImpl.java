package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.utils.API.OpenAI.DTO.GameNameDTO;
import rocha.andre.api.domain.utils.API.OpenAI.GPTQuery;
import rocha.andre.api.service.ChatGPTService;

@Service
public class ChatGPTServiceImpl implements ChatGPTService {
    @Autowired
    private GPTQuery gptQuery;

    @Override
    public String getGameInfo(GameNameDTO gameNameDTO) {
        return gptQuery.getGameInfo(gameNameDTO);
    }
}
