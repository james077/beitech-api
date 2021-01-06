package com.beitech.store.config;

import com.beitech.store.config.properties.CorsProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@AllArgsConstructor
public class CorsConfig {

    private CorsProperties corsProperties;

    /**
     * Method cors filter
     *
     * @return Filter Registrations Beans
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        corsProperties.getAllowedOrigins().forEach(config::addAllowedOrigin);
        corsProperties.getAllowedHeaders().forEach(config::addAllowedHeader);
        corsProperties.getAllowedMethods().forEach(config::addAllowedMethod);
        source.registerCorsConfiguration(corsProperties.getPathCors(), config);
        return new CorsFilter(source);
    }

}
