package com.wora.comptetition.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends CustomJpaRepository<Competition, CompetitionId> {
}
