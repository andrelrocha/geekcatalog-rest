package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.genres.DTO.GenreDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocha.andre.api.infra.utils.stringFormatter.StringFormatter;
import rocha.andre.api.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class CreateFullFameAdmin {
    @Autowired
    private GameService gameService;
    @Autowired
    private GameGenreService gameGenreService;
    @Autowired
    private GameStudioService gameStudioService;
    @Autowired
    private GameConsoleService gameConsoleService;

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private StudioService studioService;

    private static final Logger logger = LoggerFactory.getLogger(CreateFullFameAdmin.class);

    @Transactional(rollbackFor = Exception.class)
    public FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data) {
        try {
            var gameDTO = new GameDTO(capitalizeEachWord(normalizeString(data.name())), data.metacritic(), data.yearOfRelease());
            var newGame = gameService.createGame(gameDTO);
            logger.info("Jogo criado com ID: {}", newGame.id());

            var newGameGenres = new ArrayList<GenreReturnDTO>();
            var newGameConsoles = new ArrayList<ConsoleReturnDTO>();
            var newGameStudios = new ArrayList<StudioReturnFullGameInfo>();

            logger.info("Processando gêneros...");
            List<String> normalizedGenres = data.genres().stream()
                    .map(StringFormatter::normalizeString)
                    .toList();

            Map<String, GenreReturnDTO> normalizedGenresWithId = genreService.getGenresByName(
                    new ArrayList<>(normalizedGenres.stream()
                            .map(GenreDTO::new)
                            .toList())
            ).stream().collect(Collectors.toMap(
                    genre -> genre.name().toLowerCase().trim(),
                    genre -> genre
            ));

            for (String genreName : normalizedGenres) {
                GenreReturnDTO genre = normalizedGenresWithId.getOrDefault(genreName, null);

                if (genre == null) {
                    logger.info("Criando novo gênero '{}'", capitalizeEachWord(genreName));
                    genre = genreService.createGenre(new GenreDTO(capitalizeEachWord(genreName)));
                } else {
                    logger.info("Gênero '{}' já existe. Associando ao jogo ID: {}", genre.name(), newGame.id());
                }

                var gameGenreDTO = new GameGenreDTO(newGame.id().toString(), genre.id().toString());
                var gameGenreCreated = gameGenreService.createGameGenre(gameGenreDTO);

                newGameGenres.add(new GenreReturnDTO(gameGenreCreated.genreId(), gameGenreCreated.genreName()));
            }

            logger.info("Processando consoles...");
            List <String> consolesWithSystemNomenclature = convertConsoles(data.consoles());
            List<String> normalizedConsoles = consolesWithSystemNomenclature.stream()
                    .map(StringFormatter::normalizeString)
                    .toList();

            Map<String, ConsoleReturnDTO> normalizedConsolesWithId = consoleService.getConsolesByName(
                    new ArrayList<>(normalizedConsoles.stream()
                            .map(ConsoleDTO::new)
                            .toList())
            ).stream().collect(Collectors.toMap(
                    console -> console.name().toLowerCase().trim(),
                    console -> console
            ));
            for (String consoleName : normalizedConsoles) {
                ConsoleReturnDTO console = normalizedConsolesWithId.getOrDefault(consoleName, null);

                if (console == null) {
                    logger.info("Criando novo console '{}'", capitalizeEachWord(consoleName));
                    console = consoleService.createConsole(new ConsoleDTO(capitalizeEachWord(consoleName)));
                } else {
                    logger.info("Console '{}' já existe. Associando ao jogo ID: {}", console.name(), newGame.id());
                }

                var gameConsoleDTO = new GameConsoleDTO(newGame.id().toString(), console.id().toString());
                var gameConsoleCreated = gameConsoleService.createGameConsole(gameConsoleDTO);

                newGameConsoles.add(new ConsoleReturnDTO(gameConsoleCreated.consoleId(), gameConsoleCreated.consoleName()));
            }

            logger.info("Processando estúdios...");
            List<String> countryNames = data.studios().stream()
                    .map(studio -> studio.countryInfo().name().common())
                    .toList();

            List<CountryReturnDTO> countries = countryService.getCountriesByName(countryNames);

            Map<String, CountryReturnDTO> normalizedCountriesWithId = countries.stream()
                    .collect(Collectors.toMap(
                            country -> normalizeString(country.name()),
                            country -> country
                    ));

            logger.info("Processando estúdios...");
            List<StudioDTO> studiosDTO = data.studios().stream()
                    .map(studio -> {
                        var normalizedCompanyName = normalizeString(studio.companyName());

                        var normalizedCountryName = normalizeString(studio.countryInfo().name().common());

                        var country = normalizedCountriesWithId.get(normalizedCountryName);

                        if (country == null) {
                            throw new IllegalArgumentException("País não encontrado no sistema: " + studio.countryInfo().name().common());
                        }

                        return new StudioDTO(normalizedCompanyName, country.id());
                    })
                    .collect(Collectors.toList());

            Map<String, StudioReturnDTO> normalizedStudiosWithId = studioService    .getStudiosByName(studiosDTO)
                    .stream().collect(Collectors.toMap(
                            studio -> normalizeString(studio.name()),
                            studio -> studio
                    ));


            for (CompanyReturnDTO studioData : data.studios()) {
                var normalizedName = normalizeString(studioData.companyName());
                StudioReturnDTO studio;

                if (normalizedStudiosWithId.containsKey(normalizedName)) {
                    studio = normalizedStudiosWithId.get(normalizedName);
                } else {
                    var studioCountry = studioData.countryInfo().name().common().toLowerCase().trim();
                    var country = normalizedCountriesWithId.get(studioCountry);
                    var studioCountryId = country.id();
                    var newStudio = new StudioDTO(capitalizeEachWord(normalizedName), studioCountryId);
                    studio = studioService.createStudio(newStudio);
                }

                logger.info("Associando estúdio '{}' ao jogo ID: {}", studio.name(), newGame.id());
                var gameStudioDTO = new GameStudioDTO(newGame.id().toString(), studio.id().toString());
                gameStudioService.createGameStudio(gameStudioDTO);

                newGameStudios.add(new StudioReturnFullGameInfo(studio.id(), studio.name()));
            }

            logger.info("Criação do jogo a partir dos dados obtidos do IGDB concluída com sucesso!");
            return new FullGameReturnDTO(newGame, newGameConsoles, newGameGenres, newGameStudios);
        } catch (ValidationException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao criar o jogo: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno ao criar o jogo.", e);
        }
    }

    public static List<String> convertConsoles(List<String> consoles) {
        List<String> normalizedConsoles = new ArrayList<>();

        for (String console : consoles) {
            var normalizedConsole = normalizeString(console);

            if (normalizedConsole.startsWith("pc")) {
                normalizedConsole = "pc";
            }

            if (normalizedConsole.equals("xbox series x|s")) {
                normalizedConsoles.add("Xbox Series X");
                normalizedConsoles.add("Xbox Series S");
            } else {
                normalizedConsoles.add(normalizedConsole);
            }
        }

        return normalizedConsoles;
    }

    //falta fazer o convert genres, por exemplo, vem rpg (...) deve alterar para rpg
}