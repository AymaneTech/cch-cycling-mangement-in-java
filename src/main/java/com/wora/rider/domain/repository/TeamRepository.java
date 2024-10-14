package com.wora.rider.domain.repository;

import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.valueObject.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, TeamId> {
}
