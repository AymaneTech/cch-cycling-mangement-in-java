package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.StageResult;
import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.comptetition.domain.valueObject.StageResultId;
import com.wora.rider.domain.valueObject.RiderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StageResultRepository extends JpaRepository<StageResult, StageResultId> {
    @Query("DELETE FROM StageResult sr WHERE sr.stage.id = :stageId AND sr.rider.id = :riderId")
    @Modifying
    void deleteByStageAndRiderIds(StageId stageId, RiderId riderId);
}
