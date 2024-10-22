package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.SubscribeToCompetitionRequestDto;
import com.wora.comptetition.application.dto.response.SubscribeToCompetitionResponseDto;
import com.wora.comptetition.application.mapper.GeneralResultMapper;
import com.wora.comptetition.application.service.GeneralResultService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.repository.GeneralResultRepository;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
    public SubscribeToCompetitionResponseDto subscribeToCompetition(SubscribeToCompetitionRequestDto dto) {
        final Rider rider = riderRepository.findById(dto.riderId())
                .orElseThrow(() -> new EntityNotFoundException(dto.competitionId()));
        final Competition competition = competitionRepository.findById(dto.competitionId())
                .orElseThrow(() -> new EntityNotFoundException(dto.competitionId()));

        GeneralResult generalResult = new GeneralResult(competition, rider);
        GeneralResult savedResult = repository.save(generalResult);
        return mapper.toResponseDto(savedResult);
    }
}
