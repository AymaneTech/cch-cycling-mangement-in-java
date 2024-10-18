package com.wora.comptetition.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.valueObject.GeneralResultId;

public interface GeneralResultRepository extends CustomJpaRepository<GeneralResult, GeneralResultId> {
}
