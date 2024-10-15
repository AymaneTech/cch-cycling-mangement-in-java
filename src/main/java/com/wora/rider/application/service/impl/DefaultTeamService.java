package com.wora.rider.application.service.impl;

import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.TeamService;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.TeamId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTeamService implements TeamService {

    private final TeamRepository repository;

    @Override
    public List<TeamResponseDto> findAll() {
    }

    @Override
    public Optional<TeamResponseDto> findById(TeamId id) {
        return Optional.empty();
    }

    @Override
    public TeamResponseDto create(TeamRequestDto dto) {
        return null;
    }

    @Override
    public TeamResponseDto update(TeamId id, TeamRequestDto dto) {
        return null;
    }

    @Override
    public void delete(TeamId id) {

    }
}
