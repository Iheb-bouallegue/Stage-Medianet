package com.example.medianet.stagemedianet.Configuration;

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

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF pour les APIs stateless
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/login", "/api/v1/register","/api/users/delete/{userId}","/api/users/disable/{userId}")  // Permet l'accès aux routes de login et de registration
                                .permitAll() // Permet à tout utilisateur d'accéder sans authentification
                                .requestMatchers("/api/v1/employees/**")  // Restrict accès à /api/v1/employees/** selon les permissions
                                .hasAnyAuthority("CAN_DELETE_EMPLOYEE", "CAN_DISABLE_EMPLOYEE") // Accès limité aux utilisateurs avec les bonnes permissions
                                .anyRequest()  // Toutes les autres requêtes
                                .authenticated() // Nécessitent une authentification
                )
                .build();
//        http
//                .authorizeRequests()
//                // Accès aux pages d'administration
//                .antMatchers("/admin/**").hasAuthority("CAN_MANAGE_SYSTEM")
//
//                // Accès aux pages utilisateurs
//                .antMatchers("/user/**").hasAuthority("CAN_VIEW_USERS")
//
//                // Accès aux formations
//                .antMatchers("/training/**").hasAuthority("CAN_VIEW_TRAININGS")
//
//                // Accès à la gestion des objectifs pour le manager
//                .antMatchers("/goal/**").hasAuthority("CAN_CREATE_GOAL")
//
//                // Accès à la gestion des évaluations
//                .antMatchers("/evaluation/**").hasAuthority("CAN_CREATE_EVALUATION")
//
//                // Accès aux données des employés pour RH
//                .antMatchers("/employee/**").hasAuthority("CAN_MANAGE_EMPLOYEES")
//
//                // Accès au tableau de bord (par exemple)
//                .antMatchers("/dashboard/**").hasAuthority("CAN_ACCESS_DASHBOARD")
//
//                // Toute autre page nécessite une authentification
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//                .permitAll();
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

}
