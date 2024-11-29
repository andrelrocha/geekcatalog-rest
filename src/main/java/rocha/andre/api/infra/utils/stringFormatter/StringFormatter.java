package rocha.andre.api.infra.utils.stringFormatter;

import java.text.Normalizer;

public class StringFormatter {
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

        var normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^\\p{ASCII}]", "");

        normalized = normalized.replace("'", "");

        normalized = normalized.toLowerCase().trim();

        return normalized;
    }
}
