package rocha.andre.api.service;

import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;

import java.util.ArrayList;
import java.util.List;

public interface FullGameService {
    GameReturnDTO manageCreateGameEntity(CreateFullGameDTO data);
    ArrayList<ConsoleReturnDTO> manageFullGameConsoles(List<String> consoles, String gameId);
    ArrayList<GenreReturnDTO> manageFullGameGenres(List<String> genres, String gameId);
    ArrayList<StudioReturnFullGameInfo> manageFullGameStudios(List<CompanyReturnDTO> studios, String gameId);
}
