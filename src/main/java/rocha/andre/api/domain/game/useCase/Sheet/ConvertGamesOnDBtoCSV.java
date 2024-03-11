package rocha.andre.api.domain.game.useCase.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.GameRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ConvertGamesOnDBtoCSV {
    @Autowired
    private GameRepository repository;

    public File convertGamesToCSV() throws IOException {
        var games = repository.findAll();

        String csvFilePath = "src/main/resources/csv/backlogondb.csv";
        File csvFile = new File(csvFilePath);

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("Name,Length,Metacritic,Excitement,Genre,Played\n");

            for (Game game : games) {
                writer.append(String.format(
                        "\"%s\",%d,%d,%d,\"%s\",%b\n",
                        game.getName(),
                        game.getLength(),
                        game.getMetacritic(),
                        game.getExcitement(),
                        game.getGenre(),
                        game.isPlayed()
                ));
            }

            return csvFile;

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("ERRO AO CRIAR O ARQUIVO!!");
        }
    }

}
