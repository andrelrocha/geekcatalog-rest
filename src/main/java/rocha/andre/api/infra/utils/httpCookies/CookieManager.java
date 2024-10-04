package rocha.andre.api.infra.utils.httpCookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    public HttpServletResponse addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie newCookie = new Cookie("refreshToken", refreshToken);
        newCookie.setHttpOnly(true); // Impede o acesso ao cookie via JavaScript
        newCookie.setSecure(false); // Use true se estiver em HTTPS
        newCookie.setPath("/"); // Define o caminho do cookie
        newCookie.setMaxAge(15 * 24 * 60 * 60); // Defina a expiração do cookie para 15 dias
        response.addCookie(newCookie);
        return response;
    }
}
