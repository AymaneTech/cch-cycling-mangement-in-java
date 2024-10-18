package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.valueObject.GeneralResultId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralResultRepository extends JpaRepository<GeneralResult, GeneralResultId> {
}
