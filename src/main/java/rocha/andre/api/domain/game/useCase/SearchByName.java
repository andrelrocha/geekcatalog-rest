package rocha.andre.api.domain.game.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;

@Component
public class SearchByName {
    @Autowired
    private GameRepository gameRepository;

    public Page<GameReturnDTO> getAllGamesByName(Pageable pageable, String nameCompare) {
        var formattedName = capitalizeWords(nameCompare);

        var gamesComparable = gameRepository.findGamesByNameContaining(formattedName, pageable).map(GameReturnDTO::new);

        return gamesComparable;
    }

    private String capitalizeWords(String str) {
        String[] words = str.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)));
                capitalized.append(word.substring(1).toLowerCase());
                capitalized.append(" ");
            }
        }

        return capitalized.toString().trim();
    }
}
