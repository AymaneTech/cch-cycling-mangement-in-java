package com.wora.rider.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.valueObject.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, TeamId> {
}
