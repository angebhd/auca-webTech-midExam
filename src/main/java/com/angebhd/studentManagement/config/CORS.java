package com.angebhd.studentManagement.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.angebhd.studentManagement.DTO.FrontEnd;

@Configuration
public class CORS implements WebMvcConfigurer {
    
    private FrontEnd frontEnd = new FrontEnd();

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins(frontEnd.getIp()) // Allowed origin(s)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*") // Allowed headers
                .allowCredentials(true); // Allow credentials like cookies or Authorization headers
    }
    
}

