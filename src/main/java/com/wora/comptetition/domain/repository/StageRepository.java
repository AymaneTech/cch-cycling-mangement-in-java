package com.wora.comptetition.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.valueObject.StageId;

public interface StageRepository extends CustomJpaRepository<Stage, StageId> {
}
