package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, StageId> {
    List<Stage> findAllByCompetitionId(CompetitionId competitionId);
}
