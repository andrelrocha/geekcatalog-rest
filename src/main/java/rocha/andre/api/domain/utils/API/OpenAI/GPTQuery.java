package rocha.andre.api.domain.utils.API.OpenAI;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.utils.API.OpenAI.DTO.GameNameDTO;

@Component
public class GPTQuery {
    public String getGameInfo(GameNameDTO gameNameDTO) {
        var service = new OpenAiService("cole aqui sua chave da OpenAI");

        var request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("give me infos about the game: " + gameNameDTO)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
