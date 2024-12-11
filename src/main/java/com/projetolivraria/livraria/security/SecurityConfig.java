package com.projetolivraria.livraria.security;

import java.util.Arrays;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Autowired
    // private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books").permitAll()
                        .requestMatchers(HttpMethod.POST, "/newBook").permitAll()
                        .requestMatchers(HttpMethod.POST, "/newErrand").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/{code}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/books/{code}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/books/{code}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/newAdmin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/allAdmins").permitAll()
                        .requestMatchers(HttpMethod.GET, "/allAdmins/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/allAdmins/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/allAdmins/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/allErrands/{code}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/allErrands").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/allErrands/{code}").permitAll()
                        .anyRequest().permitAll()
                        // .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200", 
            "https://livraria-front-end-admin.vercel.app", 
            "https://livraria-front-end-user.vercel.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
