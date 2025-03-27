package com.ng.authen.rbac_app.config;

import com.ng.authen.rbac_app.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Environment environment;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inject the spring.h2.console.enabled property
    @Value("${spring.h2.console.enabled:false}")
    private boolean h2ConsoleEnabled;

    /**
     * Instantiates a new Security config.
     *
     * @param environment             the environment
     * @param jwtAuthenticationFilter the jwt authentication filter
     */
    public SecurityConfig(Environment environment,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.environment = environment;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Check if the 'dev' profile is active
        boolean isDevProfile = Arrays.asList(environment.getActiveProfiles()).contains("dev");

        http
                .csrf(csrf -> {
//                    if (isDevProfile && h2ConsoleEnabled) {
//                        // Disable CSRF for H2 console only in dev environment
//                        csrf.ignoringRequestMatchers("/h2-console/**");
//                    } else {
                    csrf.disable();
//                    }
                })
                // Configure session management (stateless for JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configure authorization rules
                .authorizeHttpRequests(auth -> {
                    if (isDevProfile) {
                        // Allow access to Swagger UI and API docs in dev profile
                        auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll();
                        if (h2ConsoleEnabled) {
                            auth.requestMatchers("/h2-console/**").permitAll();
                        }
                    } else {
                        // In non-dev profiles, restrict Swagger UI to ADMIN users
                        auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN");
                    }
                    // Public endpoints
                    auth.requestMatchers("/api/auth/**").permitAll();
                    // Role-based access
                    auth.requestMatchers("/api/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN");
                    // All other requests require authentication
                    auth.anyRequest().authenticated();
                })
                .headers(headers -> {
                    if (isDevProfile && h2ConsoleEnabled) {
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                    }
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Authentication manager authentication manager.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}