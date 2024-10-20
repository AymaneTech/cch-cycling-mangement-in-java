package com.wora.comptetition.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;

import java.util.List;

public interface StageRepository extends CustomJpaRepository<Stage, StageId> {
    List<Stage> findAllByCompetitionId(CompetitionId competitionId);
}
