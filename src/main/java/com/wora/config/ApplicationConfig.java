package com.wora.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.wora")
@Import({PersistenceConfig.class, WebMvcConfig.class})
public class ApplicationConfig {
}
