package com.wora.rider.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.TeamService;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.TeamId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultTeamService implements TeamService {

    private final TeamRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<TeamResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public TeamResponseDto findById(TeamId id) {
        return repository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public TeamResponseDto create(TeamRequestDto dto) {
        System.out.println("here here");
        System.out.println(dto);
        Team savedTeam = repository.save(mapper.map(dto, Team.class));
        System.out.println(savedTeam);
        return toResponseDto(savedTeam);
    }

    @Override
    public TeamResponseDto update(TeamId id, TeamRequestDto dto) {
        final Team team = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        mapper.map(dto, team);

        return mapper.map(team, TeamResponseDto.class);
    }

    @Override
    public void delete(TeamId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id);

        repository.softDeleteById(id);
    }

    private TeamResponseDto toResponseDto(Team team) {
        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getCountry(),
                team.getRiders().stream().map(r ->
                                new RiderResponseDto(r.getId(), r.getName(), r.getNationality(), r.getDateOfBirth(), null))
                        .toList()
        );
    }
}
