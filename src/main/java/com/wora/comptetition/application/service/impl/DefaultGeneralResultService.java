package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.GeneralResultRequestDto;
import com.wora.comptetition.application.dto.response.GeneralResultResponseDto;
import com.wora.comptetition.application.mapper.GeneralResultMapper;
import com.wora.comptetition.application.service.GeneralResultService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.exception.CompetitionClosedException;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.repository.GeneralResultRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.GeneralResultId;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.repository.RiderRepository;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultGeneralResultService implements GeneralResultService {
    private final GeneralResultRepository repository;
    private final RiderRepository riderRepository;
    private final CompetitionRepository competitionRepository;
    private final GeneralResultMapper mapper;

    @Override
    public List<GeneralResultResponseDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public GeneralResultResponseDto findByCompetitionIdAndRiderId(CompetitionId competitionId, RiderId riderId) {
        final GeneralResultId generalResultId = new GeneralResultId(riderId, competitionId);
        return repository.findById(generalResultId)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("general result", generalResultId));
    }

    @Override
    public GeneralResultResponseDto subscribeToCompetition(GeneralResultRequestDto dto) {
        final Rider rider = riderRepository.findById(new RiderId(dto.riderId()))
                .orElseThrow(() -> new EntityNotFoundException("rider", dto.riderId()));
        final Competition competition = competitionRepository.findById(new CompetitionId(dto.competitionId()))
                .orElseThrow(() -> new EntityNotFoundException("competition ", dto.competitionId()));

        if (competition.isClosed())
            throw new CompetitionClosedException("The competition you are trying to subscribe to is already closed");

        GeneralResult generalResult = new GeneralResult(competition, rider);
        GeneralResult savedResult = repository.save(generalResult);
        return mapper.toResponseDto(savedResult);
    }

    @Override
    public void delete(CompetitionId competitionId, RiderId riderId) {
        final GeneralResultId generalResultId = new GeneralResultId(riderId, competitionId);
        if(!repository.existsById(generalResultId))
            throw new EntityNotFoundException("general result", generalResultId);
        repository.deleteByCompetitionAndRiderIds(competitionId, riderId);
    }
}
