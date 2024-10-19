package com.wora.rider.infrastructure.seeder;

import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.TeamRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamSeeder {

    @Bean
    public ApplicationListener<ContextRefreshedEvent> run(TeamRepository teamRepository) {
        return event -> {
            System.out.println("application initialized");

            teamRepository.saveAll(
                    List.of(
                            new Team("kacm", "maroc"),
                            new Team("agadir", "maroc")
                    )
            );
        };
    }

}
