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
        return toResponseDto(savedRider);
    }

    @Override
    public RiderResponseDto update(RiderId id, RiderRequestDto dto) {
        final Rider rider = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        final Team team = teamRepository.findById(new TeamId(dto.teamId()))
                .orElseThrow(() -> new EntityNotFoundException(id));
        mapper.map(dto, rider);
        rider.setTeam(team);

        return toResponseDto(rider);
    }

    @Override
    public void delete(RiderId id) {
        if(! repository.existsById(id))
            throw new EntityNotFoundException(id);

        repository.deleteById(id);
    }

    private RiderResponseDto toResponseDto(Rider rider) {
        return mapper.map(rider, RiderResponseDto.class);
    }
}
