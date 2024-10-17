package com.wora.comptetition.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.comptetition.domain.valueObject.StageResultId;

public interface StageResultRepository extends CustomJpaRepository<StageResult, StageResultId> {
}
