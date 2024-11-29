package rocha.andre.api.domain.utils.API.IGDB.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class GenreNameFormatterFromIGDB {
    public List<String> normalizeAndConvertNames(List<String> genres) {
        List<String> normalizedGenres = new ArrayList<>();

        for (String genre : genres) {
            var normalizedGenre = normalizeString(genre);

            if (normalizedGenre.startsWith("real time strategy")) {
                normalizedGenres.add("Real Time Strategy");
            } else if (normalizedGenre.startsWith("role-playing")) {
                normalizedGenres.add("RPG");
            } else if (normalizedGenre.startsWith("turn-based strategy")) {
                normalizedGenres.add("Turn-based Strategy");
            } else if (normalizedGenre.startsWith("hack and slash/beat 'em up")) {
                normalizedGenres.add("Hack and Slash");
                normalizedGenres.add("Beat em Up");
            } else {
                normalizedGenres.add(normalizedGenre);
            }
        }

        return normalizedGenres;
    }
}
