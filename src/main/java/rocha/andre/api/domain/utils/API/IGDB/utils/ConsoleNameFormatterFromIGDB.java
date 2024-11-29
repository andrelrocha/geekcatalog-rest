package rocha.andre.api.domain.utils.API.IGDB.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class ConsoleNameFormatterFromIGDB {
    public List<String> normalizeAndConvertNames(List<String> consoles) {
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
}
