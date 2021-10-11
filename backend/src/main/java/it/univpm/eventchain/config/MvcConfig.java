package it.univpm.eventchain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	@Override
    public final void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedHeaders("*")
        .allowedOrigins("http://localhost","https://localhost","http://localhost:3000","https://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS","PATCH");
    }

}
