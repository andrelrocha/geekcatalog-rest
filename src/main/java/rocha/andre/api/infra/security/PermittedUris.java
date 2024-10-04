package rocha.andre.api.infra.security;

import java.util.Arrays;
import java.util.List;

public class PermittedUris {
    private static final List<String> PERMITTED_URIS = Arrays.asList(
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/user/**",
            "/infra/verifyjwt/**",
            "/infra/download/**",
            "/infra/ping",
            "/countries/**"
    );

    public static List<String> getPermittedUris() {
        return PERMITTED_URIS;
    }

    public static boolean isPermittedUrl(String requestURI) {
        return PERMITTED_URIS.stream().anyMatch(requestURI::matches);
    }
}
