package com.wora.rider.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.service.RiderService;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.RiderRepository;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.RiderId;
import com.wora.rider.domain.valueObject.TeamId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRiderService implements RiderService {
    private final RiderRepository repository;
    private final TeamRepository teamRepository;
    private final ModelMapper mapper;

    @Override
    public List<RiderResponseDto> findAll() {
        return repository.findAll()
                .stream().map(this::toResponseDto)
                .toList();
    }

    @Override
    public RiderResponseDto findById(RiderId id) {
        return repository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public RiderResponseDto create(RiderRequestDto dto) {
        final Team team = teamRepository.findById(new TeamId(dto.teamId()))
                .orElseThrow(() -> new EntityNotFoundException(dto.teamId()));

        final Rider mappedRider = mapper.map(dto, Rider.class)
                .setTeam(team);
        final Rider savedRider = repository.save(mappedRider);
        return mapper.map(savedRider, RiderResponseDto.class);
    }

    @Override
    public RiderResponseDto update(RiderId id, RiderRequestDto dto) {
        return null;
    }

    @Override
    public void delete(RiderId id) {

    }

    private RiderResponseDto toResponseDto(Rider rider) {
        return mapper.map(rider, RiderResponseDto.class);
    }
}
