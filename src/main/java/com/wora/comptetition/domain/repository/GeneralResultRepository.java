package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.GeneralResultId;
import com.wora.rider.domain.valueObject.RiderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GeneralResultRepository extends JpaRepository<GeneralResult, GeneralResultId> {
    @Query("DELETE FROM GeneralResult gr WHERE gr.competition.id = :competitionId AND gr.rider.id = :riderId")
    @Modifying
    void deleteByCompetitionAndRiderIds(CompetitionId competitionId, RiderId riderId);
}
