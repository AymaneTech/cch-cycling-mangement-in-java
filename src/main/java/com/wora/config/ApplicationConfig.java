package com.wora.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfig.class, ModelMapperConfig.class})
public class ApplicationConfig {
}
