package rocha.andre.api.domain.fullGame_mobile.DTO;

import rocha.andre.api.domain.game.Game;

import java.util.ArrayList;
import java.util.UUID;

public record FullGameUserDTO(UUID id, String name, Integer metacritic, Integer yearOfRelease,
                                ArrayList<String> studios, ArrayList<String> genres, ArrayList<String> consoles, String imageUrl) {

    public FullGameUserDTO(Game game, ArrayList<String> studios, ArrayList<String> genres, ArrayList<String> consoles, String imageUrl) {
        this(game.getId(), game.getName(), game.getMetacritic(), game.getYearOfRelease(), studios, genres, consoles, imageUrl);
    }
}