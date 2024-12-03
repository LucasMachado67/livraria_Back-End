package com.projetolivraria.livraria.security.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedOrigins("https://livraria-front-end-admin.vercel.app")
                .allowedOrigins("https://livraria-front-end-user.vercel.app/home")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
