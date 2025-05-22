package com.example.medianet.stagemedianet.Configuration;

import com.example.medianet.stagemedianet.Filter.JwtFilter;
import com.example.medianet.stagemedianet.Services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF pour les APIs stateless
                .addFilterBefore(new JwtFilter(customUserDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/logout","/api/v1/login", "/api/v1/register","/api/users/delete/{userId}","/api/users/team/{managerId}","/api/users/disable/{userId}","/api/users/managers","/api/users/update/{Id}","/api/formation/save","/api/formation/update/{id}","/api/formation/delete/{id}","/api/formation/get/{id}"," /api/evaluations/user/{userId}","/api/evaluations/save","/api/formation/getall","/api/formation/reserver/{id}","/api/users/all","/api/profile/save","/api/profile/upload-image/{id}","/api/profile/me","/api/evaluations/mes-evaluations","/api/formation/mes-reservations/{utilisateurId}")  // Permet l'accès aux routes de login et de registration
                                .permitAll() // Permet à tout utilisateur d'accéder sans authentification
                                .requestMatchers("/api/v1/employees/**")  // Restrict accès à /api/v1/employees/** selon les permissions
                                .hasAnyAuthority("CAN_DELETE_EMPLOYEE", "CAN_DISABLE_EMPLOYEE") // Accès limité aux utilisateurs avec les bonnes permissions
                                .anyRequest()  // Toutes les autres requêtes
                                .authenticated()// Nécessitent une authentification

                )
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // autorise toutes les routes
                        .allowedOrigins("http://localhost:4200") // frontend Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS") // méthodes autorisées
                        .allowedHeaders("*") ;// tous les headers autorisés

            }
        };
    }
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:uploads/");
        }
    }


}
