package rocha.andre.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import rocha.andre.api.domain.user.User;

import java.io.IOException;
import java.util.Collection;
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
        //String refreshToken = getRefreshToken(request);

        if (accessToken != null && tokenService.isAccessTokenValid(accessToken)) {
            String subject = tokenService.getSubject(accessToken);
            User user;

            // Verifica se o usuário está no cache
            if (userCache.containsKey(subject)) {
                user = userCache.get(subject);
            } else {
                user = authenticateUserWithValidJwt.findUserAuthenticated(subject);
                if (user != null) {
                    userCache.put(subject, user);
                }
            }

            if (user != null) {
                Collection<String> authoritiesString = tokenService.getAuthorities(accessToken);
                var authorities = authoritiesString.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // Avisa ao Spring que o usuário está autenticado
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getRefreshToken(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        return refreshToken;
    }

    private String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
