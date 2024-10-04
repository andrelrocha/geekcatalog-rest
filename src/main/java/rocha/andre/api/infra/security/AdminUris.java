package rocha.andre.api.infra.security;

import java.util.Arrays;
import java.util.List;

public class AdminUris {
    private static final List<String> ADMIN_URIS = Arrays.asList(
            "/fullgame/admin/**",
            "/games/**",
            "/consoles/**",
            "/gameconsole/**",
            "/gamestudio/**",
            "/gamegenre/**"
    );

    public static List<String> getAdminUris() {
        return ADMIN_URIS;
    }

    public static boolean isAdminUrl(String requestURI) {
        return ADMIN_URIS.stream().anyMatch(requestURI::matches);
    }
}
