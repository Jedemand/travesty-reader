package com.travesty.reader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class webConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/reader/**")
        .allowedOrigins("http://localhost:1730/", 
        "http://localhost:1713", "http://localhost:1729");
    }


}
