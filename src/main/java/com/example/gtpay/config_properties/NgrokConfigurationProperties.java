package com.example.gtpay.config_properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ngrok")
public class NgrokConfigurationProperties {
    private String basePath;
}
