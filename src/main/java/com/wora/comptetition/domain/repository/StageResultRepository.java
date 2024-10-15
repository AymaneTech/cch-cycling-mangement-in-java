package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.StageResult;
import com.wora.comptetition.domain.valueObject.StageResultId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageResultRepository extends JpaRepository<StageResult, StageResultId> {
}
