package com.wora.rider.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.mapper.TeamMapper;
import com.wora.rider.application.service.TeamService;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.TeamId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultTeamService implements TeamService {

    private final TeamRepository repository;
    private final TeamMapper mapper;

    @Override
    public List<TeamResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public TeamResponseDto findById(TeamId id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public TeamResponseDto create(TeamRequestDto dto) {
        System.out.println("here here");
        System.out.println(dto);
        Team savedTeam = repository.save(mapper.toEntity(dto));
        return mapper.toResponseDto(savedTeam);
    }

    @Override
    public TeamResponseDto update(TeamId id, TeamRequestDto dto) {
        final Team team = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        team.setName(dto.name())
                .setCountry(dto.country());
        return mapper.toResponseDto(team);
    }

    @Override
    public void delete(TeamId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id);

        repository.deleteById(id);
    }
}
