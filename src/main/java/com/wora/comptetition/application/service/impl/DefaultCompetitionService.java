package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.mapper.CompetitionMapper;
import com.wora.comptetition.application.service.CompetitionService;
import com.wora.comptetition.application.service.StageValidatorService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultCompetitionService implements CompetitionService {
    private final CompetitionRepository repository;
    private final StageValidatorService stageValidatorService;
    private final CompetitionMapper mapper;

    @Override
    public List<CompetitionResponseDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public CompetitionResponseDto findById(CompetitionId id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public CompetitionResponseDto create(CompetitionRequestDto dto) {
        Competition mappedCompetition = mapper.toEntity(dto);
        mappedCompetition._setStages(stageValidatorService.validateDtoAndGetStages(dto.stages()));
        //todo: still need to validate that all stages dates are between competition start and end date

        final Competition competition = repository.save(mappedCompetition);
        return mapper.toResponseDto(competition);
    }

    @Override
    public CompetitionResponseDto update(CompetitionId id, CompetitionRequestDto dto) {
        final Competition competition = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        competition.setName(dto.name())
                .setStartDate(dto.startDate())
                .setEndDate(dto.endDate());
        return mapper.toResponseDto(competition);
    }

    @Override
    public void delete(CompetitionId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id);
        repository.deleteById(id);
    }
}
