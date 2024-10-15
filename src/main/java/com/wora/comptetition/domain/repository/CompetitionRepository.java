package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, CompetitionId> {
}
