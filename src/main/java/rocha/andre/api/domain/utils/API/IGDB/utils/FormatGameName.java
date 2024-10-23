package rocha.andre.api.domain.utils.API.IGDB.utils;

import org.springframework.stereotype.Component;

@Component
public class FormatGameName {
    public String formatGameName(String gameName) {
        String[] words = gameName.split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        var lowerCaseWords = new String[]{"the", "of", "and", "in", "on", "at", "for", "with", "a", "an"};

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();

            if (i == 0 || !isLowerCaseWord(word, lowerCaseWords)) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            } else {
                formattedName.append(word);
            }

            if (i < words.length - 1) {
                formattedName.append(" ");
            }
        }

        return formattedName.toString();
    }

    private boolean isLowerCaseWord(String word, String[] lowerCaseWords) {
        for (String lowerWord : lowerCaseWords) {
            if (word.equals(lowerWord)) {
                return true;
            }
        }
        return false;
    }
}
