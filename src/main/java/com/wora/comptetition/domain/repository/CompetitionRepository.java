package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompetitionRepository extends JpaRepository<Competition, CompetitionId> {
    @Query("""
            UPDATE Competition c
            SET c.closed = true
            WHERE c.closed = false AND c.endDate <= CURRENT_DATE
            """)
    @Modifying
    int closeExpiredCompetitions();
}
