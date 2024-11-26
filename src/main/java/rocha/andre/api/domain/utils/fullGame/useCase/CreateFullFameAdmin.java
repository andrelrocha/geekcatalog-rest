package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
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

    private static final Logger logger = LoggerFactory.getLogger(CreateFullFameAdmin.class);

    @Transactional(rollbackFor = Exception.class)
    public FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data) {
        try {
            var gameDTO = new GameDTO(capitalizeEachWord(normalizeString(data.name())), data.metacritic(), data.yearOfRelease());
            var newGame = createGame.createGame(gameDTO);
            logger.info("Jogo criado com ID: {}", newGame.id());

            var newGameGenres = new ArrayList<GenreReturnDTO>();
            var newGameConsoles = new ArrayList<ConsoleReturnDTO>();
            var newGameStudios = new ArrayList<StudioReturnFullGameInfo>();

            logger.info("Processando gêneros...");
            List<String> normalizedGenres = data.genres().stream()
                    .map(CreateFullFameAdmin::normalizeString)
                    .toList();

            Map<String, GenreReturnDTO> normalizedGenresWithId = getGenresIdByName.getGenresByName(
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
                    genre = createGenre.createGenre(new GenreDTO(capitalizeEachWord(genreName)));
                } else {
                    logger.info("Gênero '{}' já existe. Associando ao jogo ID: {}", genre.name(), newGame.id());
                }

                var gameGenreDTO = new GameGenreDTO(newGame.id().toString(), genre.id().toString());
                var gameGenreCreated = createGameGenre.createGameGenre(gameGenreDTO);

                newGameGenres.add(new GenreReturnDTO(gameGenreCreated.genreId(), gameGenreCreated.genreName()));
            }

            logger.info("Processando consoles...");
            List <String> consolesWithSystemNomenclature = convertConsoles(data.consoles());
            List<String> normalizedConsoles = consolesWithSystemNomenclature.stream()
                    .map(CreateFullFameAdmin::normalizeString)
                    .toList();

            Map<String, ConsoleReturnDTO> normalizedConsolesWithId = getConsolesIdByName.getConsolesByName(
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
                    console = createConsole.createConsole(new ConsoleDTO(capitalizeEachWord(consoleName)));
                } else {
                    logger.info("Console '{}' já existe. Associando ao jogo ID: {}", console.name(), newGame.id());
                }

                var gameConsoleDTO = new GameConsoleDTO(newGame.id().toString(), console.id().toString());
                var gameConsoleCreated = createGameConsole.createGameConsole(gameConsoleDTO);

                newGameConsoles.add(new ConsoleReturnDTO(gameConsoleCreated.consoleId(), gameConsoleCreated.consoleName()));
            }

            logger.info("Processando estúdios...");
            List<String> countryNames = data.studios().stream()
                    .map(studio -> studio.countryInfo().name().common())
                    .toList();

            List<CountryReturnDTO> countries = getCountriesByName.getCountriesByName(countryNames);

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

            Map<String, StudioReturnDTO> normalizedStudiosWithId = getStudiosIdByName.getStudiosByName(studiosDTO)
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
                    studio = createStudio.createStudio(newStudio);
                }

                logger.info("Associando estúdio '{}' ao jogo ID: {}", studio.name(), newGame.id());
                var gameStudioDTO = new GameStudioDTO(newGame.id().toString(), studio.id().toString());
                createGameStudio.createGameStudio(gameStudioDTO);

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

    public static String capitalizeEachWord(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+");
        var capitalizedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedString.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalizedString.toString().trim();
    }

    public static String normalizeString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^\\p{ASCII}]", "");

        normalized = normalized.replace("'", "");

        normalized = normalized.toLowerCase().trim();

        return normalized;
    }

    public static List<String> convertConsoles(List<String> consoles) {
        List<String> normalizedConsoles = new ArrayList<>();

        for (String console : consoles) {
            String normalizedConsole = normalizeString(console);

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
}