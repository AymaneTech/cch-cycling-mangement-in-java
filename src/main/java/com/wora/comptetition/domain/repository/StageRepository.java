package com.wora.comptetition.domain.repository;

import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.valueObject.StageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, StageId> {
}
