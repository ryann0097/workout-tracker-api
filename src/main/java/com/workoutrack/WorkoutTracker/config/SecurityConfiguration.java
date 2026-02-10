package com.workoutrack.WorkoutTracker.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
        private final JwtAuthenticatorFilter jwtAuthenticatorFilter;
        
        public SecurityConfiguration(JwtAuthenticatorFilter jwtAuthenticatorFilter) {
                this.jwtAuthenticatorFilter = jwtAuthenticatorFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .cors(Customizer.withDefaults())
                        .csrf(csrf -> csrf.disable())
                        .sessionManagement(session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .exceptionHandling(ex -> ex
                                .authenticationEntryPoint((req, res, e) ->
                                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                                )
                                .accessDeniedHandler((req, res, e) ->
                                        res.sendError(HttpServletResponse.SC_FORBIDDEN)
                                )
                        )
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/actuator/**",
                                        "/h2-console/**",
                                        "/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET, "/exercicios/**").permitAll()
                                .anyRequest().authenticated()
                        )
                        .headers(headers ->
                                headers.frameOptions(frame -> frame.disable())
                        )
                        .addFilterBefore(jwtAuthenticatorFilter,
                                UsernamePasswordAuthenticationFilter.class
                        );
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }
}
