package com.wora.rider.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.valueObject.TeamId;

public interface TeamRepository extends CustomJpaRepository<Team, TeamId> {
}
