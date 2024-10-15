package com.wora;

import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.config.ModelMapperConfig;
import com.wora.config.PersistenceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(PersistenceConfig.class, ModelMapperConfig.class)) {
            CompetitionRepository bean = context.getBean(CompetitionRepository.class);
            System.out.println(bean);
        }
    }
}