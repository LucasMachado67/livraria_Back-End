package com.projetolivraria.livraria.security;

import java.util.Arrays;
import java.util.List;

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


//NEED's REFACTOR
@Configuration
@EnableWebSecurity
public class SecurityConfig {
        //This class needs to be fixed to support the login
//    @Autowired
//    SecurityFilter securityFilter;
//    private final CorsConfigurationSource corsConfigurationSource;
//
//    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
//        this.corsConfigurationSource = corsConfigurationSource;
//    }
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/book").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/book/new").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/newErrand").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/book/{code}").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/book/{code}").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/book/{code}").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/admin/new").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/admin/all").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/admin/{id}").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/admin/{id}").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/admin/{id}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/allErrands/{code}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/allErrands").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/allErrands/{code}").permitAll()
//                        .anyRequest().permitAll()
                            //anyRequest().permitAll() was not working so I put all the end points
//                )
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource));
//        return http.build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:4200"
//        ));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}
