package com.wora.rider.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.mapper.RiderMapper;
import com.wora.rider.application.service.RiderService;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.RiderRepository;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.RiderId;
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
public class DefaultRiderService implements RiderService {
    private final RiderRepository repository;
    private final TeamRepository teamRepository;
    private final RiderMapper mapper;

    @Override
    public List<RiderResponseDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public RiderResponseDto findById(RiderId id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public RiderResponseDto create(RiderRequestDto dto) {
        final Team team = teamRepository.findById(new TeamId(dto.teamId()))
                .orElseThrow(() -> new EntityNotFoundException("team", dto.teamId()));

        final Rider mappedRider = mapper.toEntity(dto)
                .setTeam(team);
        final Rider savedRider = repository.save(mappedRider);
        return mapper.toResponseDto(savedRider);
    }

    @Override
    public RiderResponseDto update(RiderId id, RiderRequestDto dto) {
        final Rider rider = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("rider", id));
        final Team team = teamRepository.findById(new TeamId(dto.teamId()))
                .orElseThrow(() -> new EntityNotFoundException("team", id));
        rider.setName(dto.name())
                .setNationality(dto.nationality())
                .setDateOfBirth(dto.dateOfBirth())
                .setTeam(team);

        return mapper.toResponseDto(rider);
    }

    @Override
    public void delete(RiderId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("rider", id);

        repository.deleteById(id);
    }
}
