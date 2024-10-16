package com.wora.rider.application.service;

import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.domain.valueObject.TeamId;

import java.util.List;

public interface TeamService {
    List<TeamResponseDto> findAll();

    TeamResponseDto findById(TeamId id);

    TeamResponseDto create(TeamRequestDto dto);

    TeamResponseDto update(TeamId id, TeamRequestDto dto);

    void delete(TeamId id);
}
