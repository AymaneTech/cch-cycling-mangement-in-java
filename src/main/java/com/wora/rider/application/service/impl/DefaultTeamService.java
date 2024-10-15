package com.wora.rider.application.service.impl;

import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.TeamService;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.exception.TeamNotFoundException;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.TeamId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTeamService implements TeamService {

    private final TeamRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<TeamResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(e -> mapper.map(e, TeamResponseDto.class))
                .toList();
    }

    @Override
    public TeamResponseDto findById(TeamId id) {
        return repository.findById(id)
                .map(e -> mapper.map(e, TeamResponseDto.class))
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Override
    public TeamResponseDto create(TeamRequestDto dto) {
        Team savedTeam = repository.save(mapper.map(dto, Team.class));
        return mapper.map(savedTeam, TeamResponseDto.class);
    }

    @Override
    public TeamResponseDto update(TeamId id, TeamRequestDto dto) {
        return null;
    }

    @Override
    public void delete(TeamId id) {

    }
}
