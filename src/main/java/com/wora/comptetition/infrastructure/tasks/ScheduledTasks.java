package com.wora.comptetition.infrastructure.tasks;

import com.wora.comptetition.domain.repository.CompetitionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScheduledTasks {
    private final CompetitionRepository repository;

    public ScheduledTasks(CompetitionRepository repository) {
        this.repository = repository;
    }

    //    @Scheduled(cron = "0/10 * * ? * *")
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void checkIfCompetitionShouldBeClosed() {
        System.out.println("Schedule ran ");
        int affectedCompetitions = repository.closeExpiredCompetitions();
    }
}