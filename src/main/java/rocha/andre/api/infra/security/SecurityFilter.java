package rocha.andre.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rocha.andre.api.domain.user.User;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticateUserWithValidJwt authenticateUserWithValidJwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJwt = getToken(request);

        if (tokenJwt != null && tokenService.isJwtTokenValid(tokenJwt)) {
            try {
                String subject = tokenService.getSubject(tokenJwt);
                User user = authenticateUserWithValidJwt.findUserAuthenticated(subject);

                if (user != null) {
                    // Informa ao Spring que o usuário está autenticado
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Loga a exceção e limpa o contexto de segurança se necessário
                SecurityContextHolder.clearContext();
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
