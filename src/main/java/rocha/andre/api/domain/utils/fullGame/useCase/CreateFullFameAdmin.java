package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.consoles.useCase.CreateConsole;
import rocha.andre.api.domain.consoles.useCase.GetConsolesIdByName;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.country.useCase.GetCountriesByName;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.useCase.CreateGame;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsole.useCase.CreateGameConsole;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.useCase.CreateGameGenre;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.gameStudio.useCase.CreateGameStudio;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.genres.useCase.CreateGenre;
import rocha.andre.api.domain.genres.useCase.GetGenresIdByName;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.studios.useCase.CreateStudio;
import rocha.andre.api.domain.studios.useCase.GetStudiosIdByName;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateFullFameAdmin {
    @Autowired
    private CreateGame createGame;
    @Autowired
    private CreateStudio createStudio;
    @Autowired
    private CreateConsole createConsole;
    @Autowired
    private CreateGenre createGenre;
    @Autowired
    private CreateGameGenre createGameGenre;
    @Autowired
    private CreateGameStudio createGameStudio;
    @Autowired
    private CreateGameConsole createGameConsole;

    @Autowired
    private GetConsolesIdByName getConsolesIdByName;
    @Autowired
    private GetCountriesByName getCountriesByName;
    @Autowired
    private GetGenresIdByName getGenresIdByName;
    @Autowired
    private GetStudiosIdByName getStudiosIdByName;

    public FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data) {
        try {
            var gameDTO = new GameDTO(data.name(), data.metacritic(), data.yearOfRelease());
            var newGame = createGame.createGame(gameDTO);

            var newGameGenres = new ArrayList<GenreReturnDTO>();
            var newGameConsoles = new ArrayList<ConsoleReturnDTO>();
            var newGameStudios = new ArrayList<StudioReturnFullGameInfo>();

            Map<String, GenreReturnDTO> normalizedGenresWithId = getGenresIdByName.getGenresByName(
                    (ArrayList<GenreDTO>) data.genres().stream()
                            .map(GenreDTO::new)
                            .toList()
            ).stream().collect(Collectors.toMap(
                    genre -> genre.name().toLowerCase().trim(),
                    genre -> genre
            ));

            for (String genreName : data.genres()) {
                String normalizedName = genreName.toLowerCase().trim();

                GenreReturnDTO genre = normalizedGenresWithId.getOrDefault(normalizedName,
                        createGenre.createGenre(new GenreDTO(genreName))
                );

                var gameGenreDTO = new GameGenreDTO(newGame.id().toString(), genre.id().toString());
                var gameGenreCreated = createGameGenre.createGameGenre(gameGenreDTO);

                newGameGenres.add(new GenreReturnDTO(gameGenreCreated.genreId(), gameGenreCreated.genreName()));
            }

            Map<String, ConsoleReturnDTO> normalizedConsolesWithId = getConsolesIdByName.getConsolesByName(
                    (ArrayList<ConsoleDTO>) data.consoles().stream()
                            .map(ConsoleDTO::new)
                            .toList()
            ).stream().collect(Collectors.toMap(
                    console -> console.name().toLowerCase().trim(),
                    console -> console
            ));

            for (String consoleName : data.consoles()) {
                String normalizedConsoleName = consoleName.toLowerCase().trim();

                ConsoleReturnDTO console = normalizedConsolesWithId.getOrDefault(normalizedConsoleName,
                        createConsole.createConsole(new ConsoleDTO(consoleName))
                );

                var gameConsoleDTO = new GameConsoleDTO(newGame.id().toString(), console.id().toString());
                var gameConsoleCreated = createGameConsole.createGameConsole(gameConsoleDTO);

                newGameConsoles.add(new ConsoleReturnDTO(gameConsoleCreated.consoleId(), gameConsoleCreated.consoleName()));
            }

            //aqui devemos converter companyreturn do igdb para o meu studioDTO
            List<String> countryNames = data.studios().stream()
                    .map(studio -> studio.countryInfo().name().common())
                    .toList();

            List<CountryReturnDTO> countries = getCountriesByName.getCountriesByName(countryNames);

            Map<String, CountryReturnDTO> normalizedCountriesWithId = countries.stream()
                    .collect(Collectors.toMap(
                            country -> country.name().toLowerCase().trim(),
                            country -> country
                    ));

            List<StudioDTO> studiosDTO = data.studios().stream()
                    .map(studio -> {
                        var normalizedCountryName = studio.countryInfo().name().common().toLowerCase().trim();

                        var country = normalizedCountriesWithId.get(normalizedCountryName);

                        if (country == null) {
                            throw new IllegalArgumentException("Country not found on system: " + studio.countryInfo().name().common());
                        }

                        return new StudioDTO(studio.companyName(), country.id());
                    }).toList();

            Map<String, StudioReturnDTO> normalizedStudiosWithId = getStudiosIdByName.getStudiosByName(studiosDTO)
                    .stream().collect(Collectors.toMap(
                        studio -> studio.name().toLowerCase().trim(),
                        studio -> studio
                    ));

            for (StudioReturnDTO studioData : normalizedStudiosWithId.values()) {
                var normalizedName = studioData.name().toLowerCase().trim();
                StudioReturnDTO studio;

                if (normalizedStudiosWithId.containsKey(normalizedName)) {
                    studio = normalizedStudiosWithId.get(normalizedName);
                } else {
                    var newStudio = new StudioDTO(studioData.name(), studioData.countryId());
                    studio = createStudio.createStudio(newStudio);
                }

                var gameStudioDTO = new GameStudioDTO(newGame.id().toString(), studio.id().toString());
                createGameStudio.createGameStudio(gameStudioDTO);

                newGameStudios.add(new StudioReturnFullGameInfo(studio.id(), studio.name()));
            }


            return new FullGameReturnDTO(newGame, newGameConsoles, newGameGenres, newGameStudios);
        } catch (ValidationException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao criar o jogo: " + e.getMessage());
            return null;
        }
    }
}