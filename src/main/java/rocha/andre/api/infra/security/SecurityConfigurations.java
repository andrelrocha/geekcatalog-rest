package rocha.andre.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilterApplication;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/user/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/infra/verifyjwt/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/infra/ping").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/countries/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "fullgame/admin/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "fullgame/admin/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/games/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/games/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/games/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/consoles/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/consoles/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/consoles/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/gameconsole/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/gameconsole/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/gameconsole/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/gamestudio/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/gamestudio/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/gamestudio/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/gamegenre/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/gamegenre/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/gamegenre/**").hasRole("ADMIN");
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilterApplication, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}