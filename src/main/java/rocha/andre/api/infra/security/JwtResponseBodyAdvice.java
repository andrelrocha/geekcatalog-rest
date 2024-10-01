package rocha.andre.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class JwtResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // Intercepta todas as respostas
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        /*
        String tokenJwt = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Verifica se o token está presente e é válido
        if (tokenJwt != null && tokenJwt.startsWith("Bearer ")) {
            tokenJwt = tokenJwt.substring(7);

            // Gere um novo refresh token
            String refreshToken = tokenService.generateRefreshToken(tokenJwt);

            // Adiciona o refresh token ao cabeçalho da resposta
            response.getHeaders().add(HttpHeaders.SET_COOKIE,
                    "refreshToken=" + refreshToken + "; HttpOnly; Secure; Path=/; Max-Age=" + 7 * 24 * 60 * 60);
        }
         */

        response.getHeaders().add(HttpHeaders.CACHE_CONTROL, "private, no-store");

        return body;
    }
}
