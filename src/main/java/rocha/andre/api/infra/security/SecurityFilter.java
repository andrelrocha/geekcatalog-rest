package rocha.andre.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rocha.andre.api.domain.user.User;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticateUserWithValidJwt authenticateUserWithValidJwt;

    // Cache para armazenar usuários autenticados
    private final ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);
        String refreshToken = getRefreshToken(request);

        if (accessToken != null && tokenService.isAccessTokenValid(accessToken)) {
            authenticateUser(tokenService.getSubject(accessToken));
        } else if (refreshToken != null && tokenService.isRefreshTokenValid(refreshToken)) {
            String subject = tokenService.getSubject(refreshToken);
            User user = getUserFromCacheOrDb(subject);
            if (user != null) {
                String newAccessToken = tokenService.generateAccessToken(user);
                String newRefreshToken = tokenService.generateRefreshToken(user);

                response.setHeader("Authorization", "Bearer " + newAccessToken);
                addRefreshTokenCookie(response, newRefreshToken);

                authenticateUser(subject);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String subject) {
        User user = getUserFromCacheOrDb(subject);

        if (user != null) {
            // Avisa ao Spring que o usuário está autenticado
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private User getUserFromCacheOrDb(String subject) {
        // Verifica se o usuário está no cache, se não tiver busca no banco de dados
        return userCache.computeIfAbsent(subject, s -> authenticateUserWithValidJwt.findUserAuthenticated(s));
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true); // Impede o acesso ao cookie via JavaScript
        cookie.setSecure(false); // Use true se estiver em HTTPS
        cookie.setPath("/"); // Define o caminho do cookie
        cookie.setMaxAge(60 * 60 * 24 * 30); // Defina a expiração do cookie para 7 dias
        response.addCookie(cookie);
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
