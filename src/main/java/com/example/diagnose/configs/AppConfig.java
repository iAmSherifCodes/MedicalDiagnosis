package com.example.diagnose.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration @Getter
public class AppConfig {
    @Value("${base.url}")
    private String baseUrl;

    @Value("${app.token}")
    private String token;

}
