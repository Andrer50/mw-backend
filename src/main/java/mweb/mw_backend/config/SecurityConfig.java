package mweb.mw_backend.config;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // desactiva CSRF
                .authorizeHttpRequests(authRequest -> authRequest
                        // API REST endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()
                        
                        // Thymeleaf web endpoints - públicos
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/web/products/**").permitAll()
                        .requestMatchers("/web/categories/**").permitAll()
                        .requestMatchers("/web/auth/login", "/web/auth/register").permitAll()
                        
                        // Thymeleaf web endpoints - requieren autenticación
                        .requestMatchers("/web/cart/**").authenticated()
                        .requestMatchers("/web/orders/**").authenticated()
                        .requestMatchers("/web/wishlist/**").authenticated()
                        .requestMatchers("/web/reviews/**").authenticated()
                        
                        // Recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        
                        .anyRequest().authenticated() // resto protegido
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
