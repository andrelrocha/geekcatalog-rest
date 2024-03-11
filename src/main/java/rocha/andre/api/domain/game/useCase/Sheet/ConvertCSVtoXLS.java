package rocha.andre.api.domain.game.useCase.Sheet;

import com.convertapi.client.Config;
import com.convertapi.client.ConversionResult;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Component
public class ConvertCSVtoXLS {
    //https://www.convertapi.com/a/auth
    @Value("${convert.api.secret}")
    private String convertApiSecret;
    public File convertCSVtoXLS(File csvFile) {
        try {
            String xlsDirectoryPath = "src/main/resources/xls/";
            Config.setDefaultSecret(convertApiSecret);
            ConversionResult result = ConvertApi.convert("csv", "xls",
                    new Param("File", Paths.get(csvFile.getAbsolutePath())),
                    new Param("Delimiter", ",")
            ).get();
            var xlsFilePath = result.saveFilesSync(Paths.get(xlsDirectoryPath));
            if (!xlsFilePath.isEmpty()) {
                return xlsFilePath.get(0).toFile();
            } else {
                throw new RuntimeException("Nenhum arquivo .xls foi gerado pela conversão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Algo aconteceu enquanto chamava a API de conversão de CSV para XLS.");
        }
    }
}
