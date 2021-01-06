package com.beitech.store.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private List<String> allowedOrigins;
    private String pathCors;
}
